#!/usr/bin/env python3
"""
PNLS Haiti - Cascade PTME
Genere l'Excel complet (3 feuilles) depuis MySQL directement.

Correction v2 : filtre id_status IN (6,8) applique aux deux niveaux
dans la CTE femmes_arv pour eviter d'inclure des patientes inactives.

Usage sur le serveur :
    pip install openpyxl pymysql
    python3 generer_excel_ptme.py

Sortie : cascade_ptme_31dec2025.xlsx
"""

import pymysql
from openpyxl import Workbook
from openpyxl.styles import Font, PatternFill, Alignment, Border, Side
from openpyxl.utils import get_column_letter
from datetime import date

# ── Connexion ────────────────────────────────────────────────────────────────
DB = dict(
    host     = "127.0.0.1",
    port     = 3310,
    user     = "root",
    password = "Mysql@Charess@2025",
    database = "consolidated_db",
    charset  = "utf8mb4",
    cursorclass = pymysql.cursors.DictCursor
)

DATE_DEB = "2025-02-01"
DATE_REF = "2025-12-31"
OUTPUT   = "cascade_ptme_31dec2025.xlsx"

# ── Couleurs ─────────────────────────────────────────────────────────────────
C = dict(
    DARK_BLUE  = "1F4E79",
    MED_BLUE   = "2E75B6",
    LIGHT_BLUE = "BDD7EE",
    VERY_LIGHT = "DEEAF1",
    RED_BG     = "FFE0E0",
    GREEN_BG   = "E2EFDA",
    WHITE      = "FFFFFF",
    GREY_HDR   = "F2F2F2",
    RED_TXT    = "C00000",
    GREEN_TXT  = "375623",
    BLACK      = "000000",
    GREY_TXT   = "595959",
)

def side(color="AAAAAA"):
    return Side(style="thin", color=color)

BORDER = Border(left=side(), right=side(), top=side(), bottom=side())

def fill(h):
    return PatternFill("solid", start_color=h)

def font(bold=False, size=9, color="000000", italic=False):
    return Font(name="Arial", bold=bold, size=size, color=color, italic=italic)

def align(h="center", wrap=False):
    return Alignment(horizontal=h, vertical="center", wrap_text=wrap)

def fmt_date(v):
    if v and hasattr(v, "strftime"):
        return v.strftime("%d/%m/%Y")
    return v if v is not None else ""


# ── CTE commune (femmes ARV) — CORRIGEE v2 ───────────────────────────────────
# Correction : AND id_status IN (6,8) applique aux deux niveaux
# pour eviter d'inclure des patientes dont le statut reel est inactif
# mais qui partagent la meme last_updated_date qu'un statut actif.

CTE_FEMMES_ARV = """
    SELECT pi.patient_id, pi.mspp_code, pi.date_started_arv,
           pi.given_name  AS prenom,
           pi.family_name AS nom,
           pi.birthdate   AS date_naissance,
           TIMESTAMPDIFF(YEAR, pi.birthdate, %(date_ref)s) AS age_ans,
           pi.identifier  AS identifiant,
           pi.st_id,
           pi.national_id AS id_national,
           psa.id_status
    FROM patient_isanteplus pi
             JOIN (
        SELECT patient_id, id_status, last_updated_date
        FROM (
                 SELECT
                     patient_id,
                     id_status,
                     last_updated_date,
                     ROW_NUMBER() OVER (PARTITION BY patient_id ORDER BY last_updated_date DESC) AS rn
                 FROM patient_status_arv
                 WHERE id_status IS NOT NULL
                   AND id_status IN (6, 8)
                   AND last_updated_date BETWEEN %(date_deb)s and %(date_ref)s
             ) t
        WHERE rn = 1
    ) psa ON pi.patient_id = psa.patient_id
    WHERE pi.gender = 'F'
      AND pi.voided = 0
"""

# ── SQL Cascade nationale ─────────────────────────────────────────────────────
SQL_CASCADE = """
WITH femmes_arv AS (
""" + CTE_FEMMES_ARV + """
),
grossesses_actives AS (
    SELECT patient_id, mspp_code
    FROM patient_pregnancy
    WHERE voided = 0
      AND start_date BETWEEN %(date_deb)s AND %(date_ref)s
      AND (end_date IS NULL OR end_date > %(date_ref)s)
),
prelevement AS (
    SELECT patient_id, mspp_code,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY visit_date DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND visit_date BETWEEN %(date_deb)s AND %(date_ref)s
),
cv_valide AS (
    SELECT patient_id, mspp_code,
           CAST(test_result AS DECIMAL(12,2)) AS valeur_cv,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY date_test_done DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND test_done = 1
      AND date_test_done BETWEEN %(date_deb)s AND %(date_ref)s
      AND test_result IS NOT NULL AND test_result != ''
      AND test_result REGEXP '^[0-9]+(\\\\.[0-9]+)?$'
      AND test_result NOT IN ('39', '299')
)
SELECT
    COUNT(DISTINCT CONCAT(fa.patient_id,'_',ga.mspp_code)) AS N1,
    COUNT(DISTINCT CASE WHEN p.rn=1  THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N2,
    COUNT(DISTINCT CASE WHEN cv.rn=1 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3,
    COUNT(DISTINCT CASE WHEN cv.rn=1 AND cv.valeur_cv >= 1000 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3a,
    COUNT(DISTINCT CASE WHEN cv.rn=1 AND cv.valeur_cv  < 1000 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3b
FROM femmes_arv fa
JOIN grossesses_actives ga ON ga.patient_id = fa.patient_id AND ga.mspp_code = fa.mspp_code
LEFT JOIN prelevement p  ON p.patient_id  = fa.patient_id AND p.mspp_code  = fa.mspp_code AND p.rn  = 1
LEFT JOIN cv_valide   cv ON cv.patient_id = fa.patient_id AND cv.mspp_code = fa.mspp_code AND cv.rn = 1
"""

# ── SQL Par site ──────────────────────────────────────────────────────────────
SQL_PAR_SITE = """
WITH femmes_arv AS (
""" + CTE_FEMMES_ARV + """
),
grossesses_actives AS (
    SELECT patient_id, mspp_code
    FROM patient_pregnancy
    WHERE voided = 0
      AND start_date BETWEEN %(date_deb)s and %(date_ref)s
      AND (end_date IS NULL OR end_date > %(date_ref)s)
),
prelevement AS (
    SELECT patient_id, mspp_code,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY visit_date DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND visit_date BETWEEN %(date_deb)s and %(date_ref)s
),
cv_valide AS (
    SELECT patient_id, mspp_code,
           CAST(test_result AS DECIMAL(12,2)) AS valeur_cv,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY date_test_done DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND test_done = 1
      AND date_test_done BETWEEN %(date_deb)s and %(date_ref)s
      AND test_result IS NOT NULL AND test_result != ''
      AND test_result REGEXP '^[0-9]+(\\\\.[0-9]+)?$'
      AND test_result NOT IN ('39', '299')
)
SELECT
    s.mspp_code, s.name AS nom_site, s.commune, s.department AS departement,
    COUNT(DISTINCT CONCAT(fa.patient_id,'_',ga.mspp_code)) AS N1,
    COUNT(DISTINCT CASE WHEN p.rn=1  THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N2,
    COUNT(DISTINCT CASE WHEN cv.rn=1 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3,
    COUNT(DISTINCT CASE WHEN cv.rn=1 AND cv.valeur_cv >= 1000 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3a,
    COUNT(DISTINCT CASE WHEN cv.rn=1 AND cv.valeur_cv  < 1000 THEN CONCAT(fa.patient_id,'_',ga.mspp_code) END) AS N3b
FROM femmes_arv fa
JOIN grossesses_actives ga ON ga.patient_id = fa.patient_id AND ga.mspp_code = fa.mspp_code
JOIN site s ON s.mspp_code = fa.mspp_code
LEFT JOIN prelevement p  ON p.patient_id  = fa.patient_id AND p.mspp_code  = fa.mspp_code AND p.rn  = 1
LEFT JOIN cv_valide   cv ON cv.patient_id = fa.patient_id AND cv.mspp_code = fa.mspp_code AND cv.rn = 1
GROUP BY s.mspp_code, s.name, s.commune, s.department
ORDER BY s.department, s.name
"""

# ── SQL Nominatif ─────────────────────────────────────────────────────────────
SQL_NOMINATIF = """
WITH femmes_arv AS (
""" + CTE_FEMMES_ARV + """
),
grossesses_dedup AS (
    SELECT patient_id, mspp_code,
           start_date AS debut_grossesse,
           end_date   AS fin_grossesse,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY start_date DESC) AS rn
    FROM patient_pregnancy
    WHERE voided = 0
      AND start_date BETWEEN %(date_deb)s and %(date_ref)s
      AND (end_date IS NULL OR end_date > %(date_ref)s)
),
prelevement_dedup AS (
    SELECT patient_id, mspp_code,
           visit_date AS date_prelevement,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY visit_date DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND visit_date BETWEEN %(date_deb)s and %(date_ref)s
),
cv_dedup AS (
    SELECT patient_id, mspp_code,
           CAST(test_result AS DECIMAL(12,2)) AS resultat_cv_cpml,
           date_test_done                     AS date_resultat_cv,
           ROW_NUMBER() OVER (PARTITION BY patient_id, mspp_code ORDER BY date_test_done DESC) AS rn
    FROM patient_laboratory
    WHERE voided = 0 AND test_id = 856 AND test_done = 1
      AND date_test_done BETWEEN %(date_deb)s and %(date_ref)s
      AND test_result IS NOT NULL AND test_result != ''
      AND test_result REGEXP '^[0-9]+(\\\\.[0-9]+)?$'
      AND test_result NOT IN ('39', '299')
)
SELECT
    s.mspp_code         AS code_site,
    s.name              AS nom_site,
    s.commune,
    s.department        AS departement,
    fa.patient_id,
    fa.identifiant,
    fa.st_id,
    fa.id_national,
    fa.prenom,
    fa.nom,
    fa.date_naissance,
    fa.age_ans,
    gd.debut_grossesse,
    gd.fin_grossesse,
    CASE fa.id_status
        WHEN 6 THEN 'Regulier (actif sous ARV)'
        WHEN 8 THEN 'Rendez-vous rate'
    END                 AS statut_arv,
    fa.date_started_arv AS date_debut_arv,
    pd.date_prelevement,
    cv.date_resultat_cv,
    cv.resultat_cv_cpml,
    CASE
        WHEN cv.resultat_cv_cpml IS NULL   THEN 'Pas de resultat'
        WHEN cv.resultat_cv_cpml >= 1000   THEN 'Echec virologique (CV >= 1000)'
        ELSE                                    'Supprimee (CV < 1000)'
    END                 AS interpretation_cv
FROM femmes_arv fa
JOIN grossesses_dedup gd
  ON gd.patient_id = fa.patient_id AND gd.mspp_code = fa.mspp_code AND gd.rn = 1
JOIN site s ON s.mspp_code = fa.mspp_code
LEFT JOIN prelevement_dedup pd
  ON pd.patient_id = fa.patient_id AND pd.mspp_code = fa.mspp_code AND pd.rn = 1
LEFT JOIN cv_dedup cv
  ON cv.patient_id = fa.patient_id AND cv.mspp_code = fa.mspp_code AND cv.rn = 1
ORDER BY s.department, s.name, fa.nom, fa.prenom
"""


# ── Feuille 1 : Cascade Nationale ────────────────────────────────────────────
def build_cascade(ws, row):
    N1, N2, N3, N3a, N3b = row["N1"], row["N2"], row["N3"], row["N3a"], row["N3b"]

    ws.sheet_view.showGridLines = False
    for col, w in [("A", 44), ("B", 18), ("C", 18)]:
        ws.column_dimensions[col].width = w

    ws.merge_cells("A1:C1")
    c = ws["A1"]
    c.value = "CASCADE PTME - Femmes Enceintes Actives sous ARV"
    c.font  = Font(name="Arial", bold=True, size=14, color=C["WHITE"])
    c.fill  = fill(C["DARK_BLUE"])
    c.alignment = align("center")
    ws.row_dimensions[1].height = 28

    ws.merge_cells("A2:C2")
    c = ws["A2"]
    c.value = f"Date de reference : 31 decembre 2025   |   Genere le : {date.today().strftime('%d/%m/%Y')}"
    c.font  = Font(name="Arial", size=9, italic=True, color=C["WHITE"])
    c.fill  = fill(C["MED_BLUE"])
    c.alignment = align("center")
    ws.row_dimensions[2].height = 16

    for i, h in enumerate(["Indicateur", "Valeur (N)", "Couverture (%)"], 1):
        cl = ws.cell(row=4, column=i, value=h)
        cl.font      = font(bold=True, size=10, color=C["WHITE"])
        cl.fill      = fill(C["MED_BLUE"])
        cl.alignment = align("center", wrap=True)
        cl.border    = BORDER
    ws.row_dimensions[4].height = 28

    data = [
        ("N1 - Femmes enceintes actives sous ARV (31/12/2025)",
         N1,  "Base (100%)",
         C["LIGHT_BLUE"], C["DARK_BLUE"]),
        ("N2 - Avec prelevement de specimen pour charge virale",
         N2,  f"{N2/N1*100:.1f}% de N1" if N1 else "-",
         C["VERY_LIGHT"], C["BLACK"]),
        ("N3 - Avec resultat de charge virale disponible",
         N3,  f"{N3/N1*100:.1f}% de N1" if N1 else "-",
         C["VERY_LIGHT"], C["BLACK"]),
        ("N3a - Charge virale >= 1 000 cp/ml  (Echec virologique)",
         N3a, f"{N3a/N3*100:.1f}% de N3" if N3 else "-",
         C["RED_BG"],   C["RED_TXT"]),
        ("N3b - Charge virale < 1 000 cp/ml  (Supprimee)",
         N3b, f"{N3b/N3*100:.1f}% de N3" if N3 else "-",
         C["GREEN_BG"], C["GREEN_TXT"]),
    ]

    for i, (label, val, pct, bg, tc) in enumerate(data):
        r = 5 + i
        ws.row_dimensions[r].height = 22
        for ci, (v, al) in enumerate([(label, "left"), (val, "center"), (pct, "center")], 1):
            cl = ws.cell(row=r, column=ci, value=v)
            cl.font      = font(bold=(i == 0 or ci == 2), size=10 if ci == 2 else 9,
                                color=tc if ci in (1, 2) else C["GREY_TXT"])
            cl.fill      = fill(bg)
            cl.alignment = align(al)
            cl.border    = BORDER

    # Avertissement ecart N2-N3
    r = 11
    ws.merge_cells(f"A{r}:C{r}")
    c = ws[f"A{r}"]
    c.value = f"Ecart N2-N3 : {N2 - N3} prelevements effectues sans resultat encore saisi dans le systeme."
    c.font  = font(size=9, italic=True, color="7F6000")
    c.fill  = fill("FFF2CC")
    c.alignment = align("left")
    c.border = Border(
        left=side("F4B942"), right=side("F4B942"),
        top=side("F4B942"),  bottom=side("F4B942")
    )
    ws.row_dimensions[r].height = 18

    notes = [
        "Methodologie :",
        "  N1 : statut ARV actif (id_status 6 ou 8) + grossesse active au 31/12/2025",
        "  N2 : prelevement pour CV (test_id=856, visit_date <= 31/12/2025)",
        "  N3 : resultat numerique de CV saisi (date_test_done <= 31/12/2025, hors codes 39 et 299)",
        "  Deduplication cross-sites : CONCAT(patient_id, '_', mspp_code)",
        "  Correction v2 : filtre id_status IN (6,8) applique aux deux niveaux de la CTE femmes_arv",
    ]
    for i, note in enumerate(notes):
        rr = 13 + i
        ws.merge_cells(f"A{rr}:C{rr}")
        c = ws[f"A{rr}"]
        c.value     = note
        c.font      = font(size=8, bold=(i == 0), color=C["GREY_TXT"])
        c.alignment = align("left")
        ws.row_dimensions[rr].height = 14


# ── Feuille 2 : Par Site ──────────────────────────────────────────────────────
def build_par_site(ws, rows):
    ws.sheet_view.showGridLines = False
    for col, w in zip(["A","B","C","D","E","F","G","H","I"],
                      [10, 30, 18, 14, 8, 8, 8, 9, 9]):
        ws.column_dimensions[col].width = w

    ws.merge_cells("A1:I1")
    c = ws["A1"]
    c.value = "CASCADE PTME PAR SITE - Au 31 decembre 2025"
    c.font  = Font(name="Arial", bold=True, size=13, color=C["WHITE"])
    c.fill  = fill(C["DARK_BLUE"])
    c.alignment = align("center")
    ws.row_dimensions[1].height = 24

    for i, h in enumerate(["Code Site","Nom Site","Commune","Departement",
                           "N1","N2","N3","N3a (>=1000)","N3b (<1000)"], 1):
        cl = ws.cell(row=3, column=i, value=h)
        cl.font      = font(bold=True, size=9, color=C["WHITE"])
        cl.fill      = fill(C["MED_BLUE"])
        cl.alignment = align("center", wrap=True)
        cl.border    = BORDER
    ws.row_dimensions[3].height = 30
    ws.freeze_panes = "A4"

    cycle = [C["VERY_LIGHT"], "EBF3FB", "F8FBFF"]
    di = -1
    prev = None

    for ri, row in enumerate(rows, 4):
        if row["departement"] != prev:
            di   = (di + 1) % len(cycle)
            prev = row["departement"]
        bg = cycle[di]

        vals  = [row["mspp_code"], row["nom_site"], row["commune"], row["departement"],
                 row["N1"], row["N2"], row["N3"], row["N3a"], row["N3b"]]
        aligns = ["center","left","left","left","center","center","center","center","center"]

        for ci, (v, al) in enumerate(zip(vals, aligns), 1):
            bg_ = (C["RED_BG"]   if ci == 8 and v and v > 0 else
                   C["GREEN_BG"] if ci == 9 and v and v > 0 else bg)
            tc  = (C["RED_TXT"]   if ci == 8 and v and v > 0 else
                   C["GREEN_TXT"] if ci == 9 and v and v > 0 else C["BLACK"])
            cl = ws.cell(row=ri, column=ci, value=v)
            cl.font      = font(size=9, bold=(ci == 5), color=tc)
            cl.fill      = fill(bg_)
            cl.alignment = align(al)
            cl.border    = BORDER
        ws.row_dimensions[ri].height = 14

    # Ligne total
    tr = 3 + len(rows) + 1
    ws.row_dimensions[tr].height = 18

    ws.cell(row=tr, column=1, value="TOTAL").font = font(bold=True, size=10, color=C["WHITE"])
    ws.cell(row=tr, column=1).fill   = fill(C["DARK_BLUE"])
    ws.cell(row=tr, column=1).border = BORDER

    ws.merge_cells(f"B{tr}:D{tr}")
    c = ws.cell(row=tr, column=2, value=f"{len(rows)} sites")
    c.font      = font(bold=True, size=10, color=C["WHITE"])
    c.fill      = fill(C["DARK_BLUE"])
    c.alignment = align("center")
    c.border    = BORDER

    last = 3 + len(rows)
    for ci, col_l in enumerate(["E","F","G","H","I"], 5):
        cl = ws.cell(row=tr, column=ci, value=f"=SUM({col_l}4:{col_l}{last})")
        cl.font      = font(bold=True, size=10, color=C["WHITE"])
        cl.fill      = fill(C["DARK_BLUE"])
        cl.alignment = align("center")
        cl.border    = BORDER


# ── Feuille 3 : Liste Nominative ─────────────────────────────────────────────
def build_nominatif(ws, rows):
    ws.sheet_view.showGridLines = False

    COLS = [
        ("Code Site",        8),  ("Nom Site",           28), ("Commune",          15),
        ("Departement",     13),  ("ID Patient",           9), ("Identifiant",      11),
        ("ST-ID",            9),  ("ID National",         13), ("Prenom",           18),
        ("Nom",             18),  ("Date Naissance",      13), ("Age",               5),
        ("Debut Grossesse", 14),  ("Fin Grossesse",       14), ("Statut ARV",        22),
        ("Date Debut ARV",  14),  ("Date Prelevement",    14), ("Date Resultat CV",  14),
        ("CV (cp/ml)",      12),  ("Interpretation CV",   26),
    ]
    NC = len(COLS)

    n_total   = len(rows)
    n_cv      = sum(1 for r in rows if r["interpretation_cv"] != "Pas de resultat")
    n_echec   = sum(1 for r in rows if "Echec" in (r["interpretation_cv"] or ""))
    n_supprim = sum(1 for r in rows if "Supprim" in (r["interpretation_cv"] or ""))

    # Titre
    ws.merge_cells(f"A1:{get_column_letter(NC)}1")
    c = ws["A1"]
    c.value     = "LISTE NOMINATIVE - Femmes enceintes actives sous ARV au 31 decembre 2025"
    c.font      = Font(name="Arial", bold=True, size=12, color=C["WHITE"])
    c.fill      = fill(C["DARK_BLUE"])
    c.alignment = align("center")
    ws.row_dimensions[1].height = 24

    # Compteurs
    ws.merge_cells(f"A2:{get_column_letter(NC)}2")
    c = ws["A2"]
    c.value = (f"Total : {n_total} patientes   |   Avec CV : {n_cv}   |   "
               f"Echec virologique : {n_echec}   |   Supprimees : {n_supprim}")
    c.font      = Font(name="Arial", size=9, italic=True, color=C["WHITE"])
    c.fill      = fill(C["MED_BLUE"])
    c.alignment = align("center")
    ws.row_dimensions[2].height = 16

    # Legende
    ws.merge_cells(f"A3:{get_column_letter(NC)}3")
    c = ws["A3"]
    c.value     = ("Legende :   Rouge = Echec virologique (CV >= 1 000 cp/ml)   |   "
                   "Vert = Supprimee (CV < 1 000 cp/ml)   |   Blanc/gris = Pas de resultat CV")
    c.font      = font(size=8, color=C["GREY_TXT"])
    c.fill      = fill(C["GREY_HDR"])
    c.alignment = align("left")
    ws.row_dimensions[3].height = 14

    # En-tetes
    for i, (h, w) in enumerate(COLS, 1):
        ws.column_dimensions[get_column_letter(i)].width = w
        cl = ws.cell(row=4, column=i, value=h)
        cl.font      = font(bold=True, size=9, color=C["WHITE"])
        cl.fill      = fill(C["DARK_BLUE"])
        cl.alignment = align("center", wrap=True)
        cl.border    = BORDER
    ws.row_dimensions[4].height = 30
    ws.freeze_panes = "A5"

    KEYS = ["code_site","nom_site","commune","departement","patient_id","identifiant",
            "st_id","id_national","prenom","nom","date_naissance","age_ans",
            "debut_grossesse","fin_grossesse","statut_arv","date_debut_arv",
            "date_prelevement","date_resultat_cv","resultat_cv_cpml","interpretation_cv"]

    LEFT_COLS = {2, 3, 4, 9, 10, 15, 20}

    for ri, row in enumerate(rows, 5):
        interp = row.get("interpretation_cv", "") or ""
        row_bg = (C["RED_BG"]   if "Echec"   in interp else
                  C["GREEN_BG"] if "Supprim" in interp else
                  C["WHITE"]    if ri % 2 == 1 else C["VERY_LIGHT"])

        for ci, key in enumerate(KEYS, 1):
            val = row.get(key)
            val = fmt_date(val)

            bold = False
            tc   = C["BLACK"]

            if ci == 20:  # interpretation
                if "Echec" in str(val or ""):
                    tc = C["RED_TXT"];   bold = True
                elif "Supprim" in str(val or ""):
                    tc = C["GREEN_TXT"]; bold = True
            elif ci == 19 and val:  # valeur CV
                try:
                    tc   = C["RED_TXT"] if float(val) >= 1000 else C["GREEN_TXT"]
                    bold = True
                except (ValueError, TypeError):
                    pass

            cl = ws.cell(row=ri, column=ci, value=val if val is not None else "")
            cl.font      = Font(name="Arial", bold=bold, size=9, color=tc)
            cl.fill      = fill(row_bg)
            cl.alignment = align("left" if ci in LEFT_COLS else "center")
            cl.border    = BORDER
        ws.row_dimensions[ri].height = 14

    # Note confidentialite
    note_r = 5 + len(rows) + 2
    ws.merge_cells(f"A{note_r}:{get_column_letter(NC)}{note_r}")
    c = ws[f"A{note_r}"]
    c.value     = "CONFIDENTIEL - Document a usage interne PNLS Haiti. Ne pas diffuser sans autorisation."
    c.font      = font(size=8, italic=True, color="7F0000")
    c.alignment = align("center")


# ── Utilitaire execution MySQL ────────────────────────────────────────────────
def run_query(conn, sql, params):
    with conn.cursor() as cur:
        cur.execute(sql, params)
        return cur.fetchall()


# ── Main ──────────────────────────────────────────────────────────────────────
def main():
    params = {
        "date_deb": DATE_DEB,
        "date_ref": DATE_REF
    }

    print("Connexion a MySQL...")
    conn = pymysql.connect(**DB)

    print("  Requete cascade nationale...")
    cascade_rows = run_query(conn, SQL_CASCADE, params)

    print("  Requete par site...")
    par_site_rows = run_query(conn, SQL_PAR_SITE, params)

    print("  Requete nominative (peut prendre quelques secondes)...")
    nominatif_rows = run_query(conn, SQL_NOMINATIF, params)

    conn.close()

    r = cascade_rows[0]
    print(f"\n  N1={r['N1']}  N2={r['N2']}  N3={r['N3']}  N3a={r['N3a']}  N3b={r['N3b']}")
    print(f"  Sites     : {len(par_site_rows)}")
    print(f"  Nominatif : {len(nominatif_rows)} patientes\n")

    wb = Workbook()
    ws1 = wb.active
    ws1.title = "Cascade Nationale"
    ws2 = wb.create_sheet("Par Site")
    ws3 = wb.create_sheet("Liste Nominative")

    print("Construction feuille 1 - Cascade Nationale...")
    build_cascade(ws1, cascade_rows[0])

    print("Construction feuille 2 - Par Site...")
    build_par_site(ws2, par_site_rows)

    print("Construction feuille 3 - Liste Nominative...")
    build_nominatif(ws3, nominatif_rows)

    wb.save(OUTPUT)
    print(f"\n  Fichier genere : {OUTPUT}")
    print(f"  Feuilles : {wb.sheetnames}")


if __name__ == "__main__":
    main()
