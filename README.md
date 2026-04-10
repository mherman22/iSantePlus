# iSantePlus Module Monorepo

Central repository for all iSantePlus OpenMRS modules used in the Haiti Health Information Exchange (Sedish HIE).

## Modules

### Sedish HIE Integration

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [mpi-client](modules/mpi-client) | `santedb-mpiclient` | 1.1.5-SNAPSHOT | Master Patient Index client — syncs patients with OpenCR |
| [registrationcore](modules/registrationcore) | `registrationcore` | 2.2.0 | Patient registration and MPI import |
| [xds-sender](modules/xds-sender) | `xds-sender` | 2.5.9 | Sends clinical documents to the Shared Health Record |
| [outgoing-exception](modules/outgoing-exception) | `outgoing-message-exceptions` | 1.1.1 | Handles failed outgoing messages |
| [labintegration](modules/labintegration) | `labintegration` | 2.3.9-SNAPSHOT | Lab order integration |

### iSantePlus Core

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [isanteplus](modules/isanteplus) | `isanteplus` | 1.3.0 | Core iSantePlus EMR module |
| [isanteplusreports](modules/isanteplusreports) | `isanteplusreports` | 1.1-SNAPSHOT | iSantePlus reporting |
| [registration](modules/registration) | `registration` | 1.0.0-SNAPSHOT | Patient registration UI |

### OpenMRS Upstream Forks

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [coreapps](modules/coreapps) | `coreapps` | 1.19.0-SNAPSHOT | Core application framework |
| [htmlformentry](modules/htmlformentry) | `htmlformentry` | 3.9.2 | HTML form entry engine |
| [htmlformentryui](modules/htmlformentryui) | `htmlformentryui` | 1.6.3 | HTML form entry UI widgets |
| [allergyui](modules/allergyui) | `allergyui` | 1.7.0 | Allergy management UI |
| [referenceapplication](modules/referenceapplication) | `referenceapplication` | 2.6.0 | Reference application framework |

## Prerequisites

- **Java 8** (JDK)
- **Maven 3.6+**

## Building

### Build all modules

```bash
mvn clean package -DskipTests
```

### Build a specific module (with its dependencies)

```bash
mvn clean package -DskipTests -pl modules/mpi-client -am
```

The `-am` (also make) flag builds any sibling modules that the target depends on.

### Build only the Sedish HIE modules

```bash
mvn clean package -DskipTests \
  -pl modules/mpi-client,modules/xds-sender,modules/registrationcore,modules/outgoing-exception,modules/labintegration \
  -am
```

### Run tests

```bash
mvn test
```

### Build a single module (if dependencies are already in local .m2)

```bash
mvn clean package -DskipTests -pl modules/xds-sender
```

## Dependency Graph

```
labintegration          (no inter-module deps)
mpi-client              (depends on: everest-core [vendored])
xds-sender              (depends on: everest-core [vendored], labintegration)
registrationcore        (depends on: mpi-client, xds-sender)
outgoing-exception      (depends on: registrationcore, xds-sender)
```

Modules not listed above depend only on OpenMRS core and upstream community modules.

## Vendored Dependencies

The `lib/maven-repo/` directory contains `org.marc.everest` artifacts (v1.1.0) that are no longer available from their original Maven repositories. These are HL7v3 data types used by the mpi-client and xds-sender modules for HL7v2 message processing.

The root `pom.xml` configures this directory as a local Maven repository so builds work without external authentication or access to defunct servers.

## OMODs

Built `.omod` files are located at `modules/<name>/omod/target/<artifact>-<version>.omod` after a successful build. These can be deployed to an OpenMRS instance by copying them to the `modules/` directory of the OpenMRS data folder.

## CI/CD

GitHub Actions workflows are configured for:

- **CI** (`ci.yml`): Runs on every push and PR to `main`. Builds all modules and uploads OMODs as artifacts.
- **Publish** (`publish.yml`): On GitHub release creation, builds all modules and attaches OMODs as release assets.

## Repository Structure

```
.github/workflows/     GitHub Actions CI/CD
etl/                   ETL SQL scripts
lib/maven-repo/        Vendored Maven dependencies
modules/
  allergyui/           OpenMRS allergy UI (upstream fork)
  coreapps/            OpenMRS core apps (upstream fork)
  htmlformentry/       HTML form entry engine (upstream fork)
  htmlformentryui/     HTML form entry UI (upstream fork)
  isanteplus/          Core iSantePlus module
  isanteplusreports/   iSantePlus reports
  labintegration/      Lab integration
  mpi-client/          MPI client (OpenCR integration)
  outgoing-exception/  Outgoing message error handling
  referenceapplication/ Reference application (upstream fork)
  registration/        Patient registration UI
  registrationcore/    Registration core (MPI import)
  xds-sender/          XDS.b document sender (SHR integration)
pom.xml                Root reactor POM (aggregator)
```

## License

Individual modules retain their original licenses. Most modules are licensed under the [Mozilla Public License 2.0](https://www.mozilla.org/en-US/MPL/2.0/) or the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
