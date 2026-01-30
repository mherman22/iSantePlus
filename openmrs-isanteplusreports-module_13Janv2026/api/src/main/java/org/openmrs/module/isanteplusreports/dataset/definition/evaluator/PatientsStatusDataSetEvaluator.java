package org.openmrs.module.isanteplusreports.dataset.definition.evaluator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.UserService;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.isanteplusreports.api.db.IsantePlusReportsDAO;
import org.openmrs.module.isanteplusreports.dataset.definitions.PatientsStatusDataSetDefinition;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.dataset.DataSet;
import org.openmrs.module.reporting.dataset.DataSetColumn;
import org.openmrs.module.reporting.dataset.DataSetRow;
import org.openmrs.module.reporting.dataset.SimpleDataSet;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.evaluator.DataSetEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.beans.factory.annotation.Autowired;

@Handler(supports = PatientsStatusDataSetDefinition.class)
public class PatientsStatusDataSetEvaluator implements DataSetEvaluator {

    private final Log log = LogFactory.getLog(getClass());

    //private IsantePlusReportsDAO dao;
    private IsantePlusReportsDAO dao;

    /**
     * @param dao the dao to set
     */
    public void setDao(IsantePlusReportsDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public IsantePlusReportsDAO getDao() {
        return dao;
    }

    @Autowired
    private DbSessionFactory sessionFactory;

//    @Autowired
//    private EmrApiProperties emrApiProperties;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PatientService patientService;
	
	/*
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/

    /**
     * @return the sessionFactory
     */
	/*public SessionFactory getSessionFactory() {
		return sessionFactory;
	}*/
    @Override
//    public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {
//        Long startTime = new Date().getTime();
//        PatientsStatusDataSetDefinition dsd = (PatientsStatusDataSetDefinition) dataSetDefinition;
//        Date startDate = ObjectUtil.nvl(dsd.getStartDate(), DateUtils.addDays(new Date(), -7));
//        Date endDate = ObjectUtil.nvl(dsd.getEndDate(), new Date());
//
//        startDate = DateUtil.getStartOfDay(startDate);
//        endDate = DateUtil.getEndOfDay(endDate);
//
//        Integer regularOnArt = dsd.getRegularOnArt();
//        Integer missingAppointment = dsd.getMissingAppointment();
//        Integer lostOfFollowUp = dsd.getLostOfFollowUp();
//        Integer deathOnArt = dsd.getDeathOnArt();
//        Integer stoppedOnArt = dsd.getStoppedOnArt();
//        Integer tranferedOnArt = dsd.getTranferedOnArt();
//
//        Integer transitionRecent = dsd.getTransitionRecent();
//        Integer transitionActive = dsd.getTransitionActive();
//        Integer transitionLostFollowUp = dsd.getTransitionLostFollowUp();
//        Integer transitionDeath = dsd.getTransitionDeath();
//        Integer transitionTranfered = dsd.getTransitionTranfered();
//
//        //PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
//
//        StringBuilder sqlQuery = new StringBuilder();
//        sqlQuery.append("" +
//                "select p.patient_id, p.st_code as 'NO. de patient attribué par le site', p.national_code as 'Numéro identité national', " +
//                "       p.given_name as 'Prénom', p.family_name as 'Nom', p.gender as 'Sexe', p.age as 'Age'," +
//                "       case when p.status = 'interrupted' then 'Interruption'" +
//                "            when p.status = 'deceased' then 'Décédé'" +
//                "            when p.status = 'missed' then 'Rendez-vous raté'" +
//                "            when p.status = 'unknown' then 'Inconnu'" +
//                "            when p.status = 'regular' then 'Actif'" +
//                "            when p.status = 'transferred' then 'Transféré'" +
//                "            when p.status = 'stopped' then 'Arrété'" +
//                "        end as 'Status du patient', p.status_date as 'Dernière date', p.address as 'Adresse', p.telephone as 'Téléphone'," +
//                "       p.mother_name as 'Contact', p.rendez_vous as 'Date de prochaine visite', p.reason as 'Raison de discontinuation'" +
//                "  from summary.patient p" +
//                " where p.status_date <= :endDate" +
//                "   and p.status in (:regularOnArt,:missingAppointment,:lostOfFollowUp,:deathOnArt,:stoppedOnArt,:tranferedOnArt)" +
//                "   and p.status is not null;");
//
//
//        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//
//
//        if (endDate != null) {
//            query.setTimestamp("endDate", endDate);
//        }
//
//        if (regularOnArt != 0) {
//            query.setString("regularOnArt", "regular");
//        } else {
//            query.setString("regularOnArt", "0");
//        }
//
//        if (missingAppointment != 0) {
//            query.setString("missingAppointment", "missed");
//        } else {
//            query.setString("missingAppointment", "0");
//        }
//
//        if (lostOfFollowUp != 0) {
//            query.setString("lostOfFollowUp", "interrupted");
//        } else {
//            query.setString("lostOfFollowUp", "0");
//        }
//
//        if (deathOnArt != 0) {
//            query.setString("deathOnArt", "deceased");
//        } else {
//            query.setString("deathOnArt", "0");
//        }
//
//        if (stoppedOnArt != 0) {
//            query.setString("stoppedOnArt", "stopped");
//        } else {
//            query.setString("stoppedOnArt", "0");
//        }
//
//        if (tranferedOnArt != 0) {
//            query.setString("tranferedOnArt", "transferred");
//        } else {
//            query.setString("tranferedOnArt", "0");
//        }
//
////		if (transitionRecent != null) {
////			query.setInteger("transitionRecent", transitionRecent);
////		}
////		if (transitionActive != null) {
////			query.setInteger("transitionActive", transitionActive);
////		}
////		if (transitionLostFollowUp != null) {
////			query.setInteger("transitionLostFollowUp", transitionLostFollowUp);
////		}
////		if (transitionDeath != null) {
////			query.setInteger("transitionDeath", transitionDeath);
////		}
////		if (transitionTranfered != null) {
////			query.setInteger("transitionTranfered", transitionTranfered);
////		}
//
//
//        List<Object[]> list = query.list();
//        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
//
//        for (Object[] o : list) {
//            DataSetRow row = new DataSetRow();
//            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[0]);
//            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[1]);
//            row.addColumnValue(new DataSetColumn("national_id", "national_id", String.class), o[2]);
//            row.addColumnValue(new DataSetColumn("prenom", "prenom", String.class), o[3]);
//            row.addColumnValue(new DataSetColumn("nom", "nom", String.class), o[4]);
//            row.addColumnValue(new DataSetColumn("sexe", "sexe", String.class), o[5]);
//            row.addColumnValue(new DataSetColumn("age", "age", String.class), o[6]);
//            row.addColumnValue(new DataSetColumn("statut", "statut", String.class), o[7]);
//            row.addColumnValue(new DataSetColumn("adresse", "adresse", String.class), o[8]);
//            row.addColumnValue(new DataSetColumn("derniere_date", "derniere_date", String.class), o[9]);
//            row.addColumnValue(new DataSetColumn("telephone", "telephone", String.class), o[10]);
//            row.addColumnValue(new DataSetColumn("contact", "contact", String.class), o[11]);
//            row.addColumnValue(new DataSetColumn("prochaine_visite", "prochaine_visite", String.class), o[12]);
//            row.addColumnValue(new DataSetColumn("raison", "raison", String.class), o[13]);
//            dataSet.addRow(row);
//        }
//
//        return dataSet;
//    }

    public DataSet evaluate(DataSetDefinition dataSetDefinition, EvaluationContext context) throws EvaluationException {
        Long startTime = new Date().getTime();
        PatientsStatusDataSetDefinition dsd = (PatientsStatusDataSetDefinition) dataSetDefinition;
        Date startDate = ObjectUtil.nvl(dsd.getStartDate(), DateUtils.addDays(new Date(), -7));
        Date endDate = ObjectUtil.nvl(dsd.getEndDate(), new Date());
        startDate = DateUtil.getStartOfDay(startDate);
        endDate = DateUtil.getEndOfDay(endDate);
        Integer regularOnArt = dsd.getRegularOnArt();
        Integer missingAppointment = dsd.getMissingAppointment();
        Integer lostOfFollowUp = dsd.getLostOfFollowUp();
        Integer deathOnArt = dsd.getDeathOnArt();
        Integer stoppedOnArt = dsd.getStoppedOnArt();
        Integer tranferedOnArt = dsd.getTranferedOnArt();
        Integer transitionRecent = dsd.getTransitionRecent();
        Integer transitionActive = dsd.getTransitionActive();
        Integer transitionLostFollowUp = dsd.getTransitionLostFollowUp();
        Integer transitionDeath = dsd.getTransitionDeath();
        Integer transitionTranfered = dsd.getTransitionTranfered();
        //PatientIdentifierType primaryIdentifierType = emrApiProperties.getPrimaryIdentifierType();
        StringBuilder sqlQuery = new StringBuilder(
                "select DISTINCT pat.patient_id, pat.st_id as 'NO. de patient attribué par le site', pat.national_id as 'Numéro identité national',"
                        + " pat.given_name as Prénom,pat.family_name as Nom, pat.gender as Sexe,"
                        + " TIMESTAMPDIFF(YEAR, pat.birthdate,DATE(now())) as Age, arv.name_fr as 'Status de patient',"
                        + " DATE_FORMAT(DATE(patstatus.start_date), '%d-%m-%Y') as 'Dernière date', pat.last_address as Adresse, pat.telephone as Téléphone,"
                        + " pat.contact_name as Contact, DATE_FORMAT(DATE(pat.next_visit_date), '%d-%m-%Y') as 'Date de prochaine visite',"
                        + " CASE WHEN(patstatus.dis_reason=5240) THEN 'Perdu de vue'"
                        + "  WHEN (patstatus.dis_reason=159492) THEN 'Transfert'"
                        + " WHEN (patstatus.dis_reason=159) THEN 'Décès'"
                        + " WHEN (patstatus.dis_reason=1667) THEN 'Discontinuations'"
                        + " WHEN (patstatus.dis_reason=1067) THEN 'Inconnue'"
                        + "END as 'Raison de discontinuation'");
        sqlQuery.append(" FROM isanteplus.patient pat");
        sqlQuery.append(" INNER JOIN isanteplus.patient_status_arv patstatus ON pat.patient_id=patstatus.patient_id");
        sqlQuery.append(" INNER JOIN isanteplus.arv_status_loockup arv ON patstatus.id_status=arv.id");
        sqlQuery.append(" INNER JOIN (SELECT ps.patient_id, MAX(ps.date_started_status) as date_started_status"
                + " FROM isanteplus.patient_status_arv ps WHERE ps.date_started_status <= :endDate GROUP BY 1) B");
        sqlQuery.append(" ON patstatus.patient_id = B.patient_id");
        sqlQuery.append(" WHERE patstatus.id_status IN (:regularOnArt,:missingAppointment,:lostOfFollowUp,"
                + ":deathOnArt, :stoppedOnArt,:tranferedOnArt,:transitionRecent,:transitionActive,"
                + ":transitionLostFollowUp,:transitionDeath, :transitionTranfered)");
        /*sqlQuery.append(" AND patstatus.start_date = B.start_date");*/
        sqlQuery.append(" AND patstatus.date_started_status = B.date_started_status");
        sqlQuery.append(" AND patstatus.date_started_status <= :endDate");
        sqlQuery.append(" AND pat.voided <> 1");
        sqlQuery.append(" ORDER BY arv.name_fr");

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
        if (endDate != null) {
            query.setTimestamp("endDate", endDate);
        }
        if (regularOnArt != null) {
            query.setInteger("regularOnArt", regularOnArt);
        }
        if (missingAppointment != null) {
            query.setInteger("missingAppointment", missingAppointment);
        }
        if (lostOfFollowUp != null) {
            query.setInteger("lostOfFollowUp", lostOfFollowUp);
        }
        if (deathOnArt != null) {
            query.setInteger("deathOnArt", deathOnArt);
        }
        if (stoppedOnArt != null) {
            query.setInteger("stoppedOnArt", stoppedOnArt);
        }
        if (tranferedOnArt != null) {
            query.setInteger("tranferedOnArt", tranferedOnArt);
        }
        if (transitionRecent != null) {
            query.setInteger("transitionRecent", transitionRecent);
        }
        if (transitionActive != null) {
            query.setInteger("transitionActive", transitionActive);
        }
        if (transitionLostFollowUp != null) {
            query.setInteger("transitionLostFollowUp", transitionLostFollowUp);
        }
        if (transitionDeath != null) {
            query.setInteger("transitionDeath", transitionDeath);
        }
        if (transitionTranfered != null) {
            query.setInteger("transitionTranfered", transitionTranfered);
        }


        List<Object[]> list = query.list();
        SimpleDataSet dataSet = new SimpleDataSet(dataSetDefinition, context);
        for (Object[] o : list) {
            DataSetRow row = new DataSetRow();
            row.addColumnValue(new DataSetColumn("patient_id", "patient_id", String.class), o[0]);
            row.addColumnValue(new DataSetColumn("st_id", "st_id", String.class), o[1]);
            row.addColumnValue(new DataSetColumn("national_id", "national_id", String.class), o[2]);
            row.addColumnValue(new DataSetColumn("prenom", "prenom", String.class), o[3]);
            row.addColumnValue(new DataSetColumn("nom", "nom", String.class), o[4]);
            row.addColumnValue(new DataSetColumn("sexe", "sexe", String.class), o[5]);
            row.addColumnValue(new DataSetColumn("age", "age", String.class), o[6]);
            row.addColumnValue(new DataSetColumn("statut", "statut", String.class), o[7]);
            row.addColumnValue(new DataSetColumn("adresse", "adresse", String.class), o[8]);
            row.addColumnValue(new DataSetColumn("derniere_date", "derniere_date", String.class), o[9]);
            row.addColumnValue(new DataSetColumn("telephone", "telephone", String.class), o[10]);
            row.addColumnValue(new DataSetColumn("contact", "contact", String.class), o[11]);
            row.addColumnValue(new DataSetColumn("prochaine_visite", "prochaine_visite", String.class), o[12]);
            row.addColumnValue(new DataSetColumn("raison", "raison", String.class), o[13]);
            dataSet.addRow(row);
        }
        return dataSet;
    }

}
