# iSantePlus — Custom Modules Workspace

This repository contains the source code for the custom OpenMRS modules that power the iSantePlus EMR in the [SEDISH HIE](https://github.com/sedish) stack for Haiti.

It is a **development workspace only** — nothing here runs directly. You build the modules here, then deploy the compiled `.omod` artifacts into the SEDISH Docker setup.

---

## Repository Structure

| Folder | Module | Purpose |
|---|---|---|
| `isanteplus-openmrs_15Dec2025/` | `isanteplus` | Core iSantePlus module — patient dashboard, growth charts, HIV treatment history, clinical workflows, MPI/OpenCR wiring |
| `openmrs-isanteplusreports-module_13Janv2026/` | `isanteplusreports` | Reporting module — HIV indicators, ETL-backed reports, PDF exports |
| `registration/` | `registration` | Patient registration module — handles HL7 PIX submission to OpenCR/MPI on patient creation |
| `openmrs-coreapps-module/` | `coreapps` | Forked OpenMRS coreapps — **not actively built** (base image ships a newer version, phantomjs build is broken) |
| `openmrs-referenceapp-module/` | `referenceapplication` | Forked reference app — **not actively built** (same version as base image) |
| `openmrs-htmlformentryui-module/` | `htmlformentryui` | Forked HTML form entry UI — **not actively built** (same version as base image) |
| `openmrs-allergyui-module/` | `allergyui` | Forked allergy UI — **not actively built** (same version as base image) |
| `etl_2.8.2/` | — | SQL scripts for the ETL reporting datamart |

---

## How This Fits Into SEDISH

Each module compiles to an `.omod` file. These files are copied into the SEDISH repo and baked into the iSantePlus Docker image at build time:

```
mvn clean package (this repo)
        │
        ▼
   *.omod artifacts  →  omods/  (git-ignored)
        │
        ▼ (copy manually)
sedish/packages/emr-isanteplus/config/custom_modules/
        │
        ▼
Dockerfile: FROM ghcr.io/isanteplus/docker-isanteplus-server:v2.3.4
            COPY ./config/custom_modules /custom_modules
        │
        ▼
itechuw/docker-isanteplus-server:local  (Docker image)
        │
        ▼
Docker Swarm: isanteplus, isanteplus2, isanteplus3, isanteplus4
```

At container startup, the base image's `startup.sh` copies everything from `/custom_modules` into `/openmrs/data/modules/`, overriding any modules shipped in the base image.

---

## Prerequisites

- Java 8
- Maven 3.x
- Access to the [SEDISH repo](../sedish) for deployment

---

## Building & Deploying

Use the `build.sh` script at the repo root. It builds all modules and collects the compiled omods into a local `omods/` folder (git-ignored).

```bash
./build.sh
```

When done, all omods are in `omods/`. Copy them manually to the SEDISH repo:

```bash
cp omods/*.omod ../sedish/packages/emr-isanteplus/config/custom_modules/
```

Then rebuild and redeploy from the SEDISH repo:

```bash
cd ../sedish
docker compose -f packages/emr-isanteplus/docker-compose.yml build
packages/emr-isanteplus/swarm.sh down && packages/emr-isanteplus/swarm.sh up
```

---

## SEDISH Integration — Patient Registration Flow

When a patient is registered in iSantePlus, the `registration` module sends an **HL7 PIX message** through OpenHIM to OpenCR (the Client Registry). OpenCR assigns or returns an **ECID** (Enterprise Client ID), which is written back to the patient record.

```
iSantePlus (registration module)
    │  HL7 PIX  →  OpenHIM :5001/pix
    ▼
OpenHIM → OpenCR/MPI
    │  returns ECID
    ▼
Patient record updated with ECID
```

The ECID is later used by the `xds-sender` module to stamp patient identity into XDS/CDA documents sent to the Shared Health Record (SHR).

Key global properties (set in OpenMRS Administration after deployment):

| Property | Default Value | Purpose |
|---|---|---|
| `registrationcore.mpi.pixEndpoint` | `http://sedish.net:5001/pix` | PIX endpoint through OpenHIM |
| `registrationcore.mpi.pdqEndpoint` | `http://sedish.net:5001/pdq` | PDQ query endpoint |
| `xdssender.repositoryEndpoint` | `http://sedish.net:5001/xdsrepository` | XDS repository through OpenHIM |
| `xdssender.mpiEndpoint` | *(set to OpenCR URL)* | Direct FHIR patient push to OpenCR |

---

## Current Custom Modules in SEDISH

The following `.omod` files are currently deployed in `sedish/packages/emr-isanteplus/config/custom_modules/`:

| File | Source |
|---|---|
| `isanteplus-1.4.6.omod` | This repo — `isanteplus-openmrs_15Dec2025/` |
| `isanteplusreports-1.1-SNAPSHOT.omod` | This repo — `openmrs-isanteplusreports-module_13Janv2026/` |
| `labintegration-2.3.6-SNAPSHOT.omod` | [`openmrs-module-labintegration`](https://github.com/IsantePlus/openmrs-module-labintegration) |
| `xds-sender-2.5.0-SNAPSHOT.omod` | [`openmrs-module-xds-sender`](https://github.com/IsantePlus/openmrs-module-xds-sender) |
