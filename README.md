# iSantePlus Module Monorepo

Central repository for all iSantePlus OpenMRS modules used in the Haiti Health Information Exchange (Sedish HIE).

## Modules

### Sedish HIE Integration

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [openmrs-module-mpi-client](openmrs-module-mpi-client) | `santedb-mpiclient` | 1.1.5-SNAPSHOT | Master Patient Index client — syncs patients with OpenCR |
| [openmrs-module-registrationcore](openmrs-module-registrationcore) | `registrationcore` | 2.2.0 | Patient registration and MPI import |
| [openmrs-module-xds-sender](openmrs-module-xds-sender) | `xds-sender` | 2.5.9 | Sends clinical documents to the Shared Health Record |
| [openmrs-module-outgoing-exception](openmrs-module-outgoing-exception) | `outgoing-message-exceptions` | 1.1.1 | Handles failed outgoing messages |
| [openmrs-module-labintegration](openmrs-module-labintegration) | `labintegration` | 2.3.9-SNAPSHOT | Lab order integration |

### iSantePlus Core

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [isanteplus-openmrs_15Dec2025](isanteplus-openmrs_15Dec2025) | `isanteplus` | 1.3.0 | Core iSantePlus EMR module |
| [openmrs-isanteplusreports-module_13Janv2026](openmrs-isanteplusreports-module_13Janv2026) | `isanteplusreports` | 1.1-SNAPSHOT | iSantePlus reporting |
| [openmrs-module-registration](openmrs-module-registration) | `registration` | 1.0.0-SNAPSHOT | Patient registration UI |

### OpenMRS Upstream Forks

| Module | Artifact | Version | Description |
|--------|----------|---------|-------------|
| [openmrs-coreapps-module](openmrs-coreapps-module) | `coreapps` | 1.19.0-SNAPSHOT | Core application framework |
| [openmrs-htmlformentry-module](openmrs-htmlformentry-module) | `htmlformentry` | 3.9.2 | HTML form entry engine |
| [openmrs-htmlformentryui-module](openmrs-htmlformentryui-module) | `htmlformentryui` | 1.6.3 | HTML form entry UI widgets |
| [openmrs-allergyui-module](openmrs-allergyui-module) | `allergyui` | 1.7.0 | Allergy management UI |
| [openmrs-referenceapp-module](openmrs-referenceapp-module) | `referenceapplication` | 2.6.0 | Reference application framework |

## Prerequisites

- **Java 8** (JDK) — required by OpenMRS platform
- **Maven 3.6+**

## Building

### Build all modules

```bash
mvn clean package -DskipTests
```

### Build a specific module (with its dependencies)

```bash
mvn clean package -DskipTests -pl openmrs-module-mpi-client -am
```

The `-am` (also make) flag automatically builds any sibling modules that the target depends on.

### Build only the Sedish HIE modules

```bash
mvn clean package -DskipTests \
  -pl openmrs-module-mpi-client,openmrs-module-xds-sender,openmrs-module-registrationcore,openmrs-module-outgoing-exception,openmrs-module-labintegration \
  -am
```

### Build a single module (if dependencies are already in local .m2)

```bash
mvn clean package -DskipTests -pl openmrs-module-xds-sender
```

### Run tests

```bash
mvn test
```

### Install to local Maven repository

```bash
mvn clean install -DskipTests
```

This installs all modules to your local `~/.m2/repository`, making them available as dependencies for other local projects (e.g., the Sedish deployment repo).

## Dependency Graph

```
labintegration          (no inter-module deps)
mpi-client              (depends on: everest-core [vendored])
xds-sender              (depends on: everest-core [vendored], labintegration)
registrationcore        (depends on: mpi-client, xds-sender)
outgoing-exception      (depends on: registrationcore, xds-sender)
```

## Adding a New Module

### From an existing external repository

1. **Clone and import** the module source (without git history):

   ```bash
   git clone --depth 1 https://github.com/IsantePlus/openmrs-module-example.git /tmp/example
   rm -rf /tmp/example/.git
   cp -r /tmp/example example
   rm -rf /tmp/example
   ```

2. **Fix repository references** in the module's `pom.xml`:
   - Remove any references to `te.marc-hi.ca`, `santesuite.org`, or other defunct repos
   - Remove `github-packages` profiles that require private registry auth
   - If the module depends on `everest-core`, add the local vendored repository:
     ```xml
     <repository>
         <id>local-vendored</id>
         <name>Vendored dependencies (everest-core)</name>
         <url>file://${session.executionRootDirectory}/lib/maven-repo</url>
     </repository>
     ```

3. **Import the BOM** — if the module depends on other monorepo modules, add the BOM to its `<dependencyManagement>`:

   ```xml
   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>org.openmrs.module</groupId>
               <artifactId>isanteplus-bom</artifactId>
               <version>1.0.0-SNAPSHOT</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   ```

   Then declare inter-module dependencies without version tags — the BOM provides them.

4. **Add the module's version to the BOM** — in `bom/pom.xml`, add a property and entries for both `-api` and `-omod` artifacts:

   ```xml
   <properties>
       <example.version>1.0.0-SNAPSHOT</example.version>
   </properties>

   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>org.openmrs.module</groupId>
               <artifactId>example-api</artifactId>
               <version>${example.version}</version>
               <scope>provided</scope>
           </dependency>
           <dependency>
               <groupId>org.openmrs.module</groupId>
               <artifactId>example-omod</artifactId>
               <version>${example.version}</version>
               <scope>provided</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   ```

5. **Add to the root `pom.xml`** — insert a `<module>` entry in the correct build order (dependencies must be listed before dependents):

   ```xml
   <modules>
       <!-- ... existing modules ... -->
       <module>example</module>
   </modules>
   ```

6. **Verify the build**:

   ```bash
   mvn clean package -DskipTests -pl example -am
   ```

7. **Update this README** — add the module to the appropriate table above and update the dependency graph if it has inter-module dependencies.

### Creating a new module from scratch

1. **Generate the OpenMRS module skeleton**:

   ```bash
   mvn archetype:generate \
     -DarchetypeGroupId=org.openmrs.maven.archetypes \
     -DarchetypeArtifactId=openmrs-owa-archetype \
     -DarchetypeVersion=1.0.1
   ```

   Or manually create the standard OpenMRS module structure:

   ```
   example/
     pom.xml            (parent POM, packaging: pom)
     api/
       pom.xml          (API module)
       src/main/java/
       src/main/resources/
       src/test/java/
     omod/
       pom.xml          (OMOD module)
       src/main/java/
       src/main/resources/
       src/main/webapp/
   ```

2. Follow steps 3-7 from the section above (BOM import, add to BOM, add to root POM, verify, update README).

### Vendoring a new external dependency

If your module depends on a JAR that is not available from Maven Central or the OpenMRS repository:

1. Place the JAR and POM in `lib/maven-repo/` following the standard Maven layout:

   ```
   lib/maven-repo/
     com/example/
       some-library/1.0.0/
         some-library-1.0.0.jar
         some-library-1.0.0.pom
   ```

2. The root `pom.xml` already configures `lib/maven-repo/` as a local repository, so no additional configuration is needed.

## Bill of Materials (BOM)

The `bom/pom.xml` centralizes version management for all inter-module dependencies. It covers both `-api` and `-omod` artifacts for every module in the monorepo, plus vendored dependencies like everest-core.

### How it works

Modules import the BOM in their `<dependencyManagement>`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.openmrs.module</groupId>
            <artifactId>isanteplus-bom</artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then inter-module dependencies are declared without version tags:

```xml
<dependency>
    <groupId>org.openmrs.module</groupId>
    <artifactId>santedb-mpiclient-api</artifactId>
    <!-- version inherited from BOM -->
</dependency>
```

### Bumping a version

All versions are defined as properties in `bom/pom.xml`. To bump a module version, change one line:

```xml
<properties>
    <mpiclient.version>1.1.5-SNAPSHOT</mpiclient.version>  <!-- change here -->
</properties>
```

Every module that depends on it picks up the new version automatically — no need to update multiple POMs.

## Vendored Dependencies

The `lib/maven-repo/` directory contains `org.marc.everest` artifacts (v1.1.0) that are no longer available from their original Maven repositories (`te.marc-hi.ca` and `santesuite.org` are both defunct). These are HL7v3 data types used by the mpi-client and xds-sender modules for HL7v2 message processing.

The root `pom.xml` configures this directory as a local Maven repository so builds work without external authentication or access to defunct servers.

## Deploying OMODs to the Sedish HIE

The Sedish HIE deployment ([charess-org/sedish](https://github.com/charess-org/sedish)) uses OMODs from `sedish/packages/emr-isanteplus/config/custom_modules/`. There are three ways to get OMODs from this repo.

### Option 1: Download from GitHub Actions (no build needed)

1. Go to the [Actions tab](../../actions)
2. Click the latest successful **"Build, Test & Publish OMODs"** run
3. Download the **omods** artifact (zip containing all OMODs)
4. Extract and copy the needed OMODs:
   ```bash
   unzip omods.zip -d /tmp/omods
   cp /tmp/omods/*.omod ../sedish/packages/emr-isanteplus/config/custom_modules/
   ```

### Option 2: Download from a release

```bash
gh release download <tag> --repo charess-org/iSantePlus --pattern "*.omod" \
   --dir ../sedish/packages/emr-isanteplus/config/custom_modules/
```

### Option 3: Build locally and copy

```bash
mvn clean package -DskipTests
cp openmrs-module-mpi-client/omod/target/*.omod ../sedish/packages/emr-isanteplus/config/custom_modules/
cp openmrs-module-registrationcore/omod/target/*.omod ../sedish/packages/emr-isanteplus/config/custom_modules/
cp openmrs-module-xds-sender/omod/target/*.omod ../sedish/packages/emr-isanteplus/config/custom_modules/
# ... copy any other OMODs you need
```

To build a single module:
```bash
mvn clean package -DskipTests -pl openmrs-module-registrationcore -am
```

### Deploying to the running Sedish HIE

After copying OMODs to custom_modules, rebuild and redeploy:

```bash
cd ../sedish
docker build -t itechuw/docker-isanteplus-server:local-2 packages/emr-isanteplus/
docker service update --force isanteplus_isanteplus
docker service update --force isanteplus_isanteplus2
```

## CI/CD

A single workflow (`build.yml`) handles building, testing, and publishing:

| Trigger | Build | Tests | Upload artifacts | Attach to release |
|---------|-------|-------|-----------------|-------------------|
| Pull request | Yes | No | Yes | No |
| Push to main | Yes | Yes | Yes | No |
| Release created | Yes | Yes | Yes | Yes |
| Manual dispatch | Yes | Yes | Yes | No |

PRs only build (no tests) to keep feedback fast. Tests run on merge to main and on releases.

### Downloading OMODs

**From CI:** Go to the Actions tab, select the workflow run, download the `omods` artifact.

**From releases:**
```bash
gh release download <tag> --pattern "*.omod"
```

### Creating a Release

Releases attach all built OMODs as downloadable assets on the release page.

**Via GitHub UI:**
1. Go to the repo's Releases page
2. Click "Draft a new release"
3. Create a new tag (e.g. `v1.0.0`)
4. Add release notes
5. Click "Publish release"
6. CI builds automatically and attaches OMODs to the release

**Via CLI:**
```bash
gh release create v1.0.0 --title "v1.0.0" --notes "Release notes here"
```

The workflow triggers automatically — OMODs appear on the release page within a few minutes.

## Repository Structure

```
.github/workflows/     GitHub Actions CI/CD
openmrs-allergyui-module/             OpenMRS allergy UI (upstream fork)
bom/                   Bill of Materials (centralized version management)
openmrs-coreapps-module/              OpenMRS core apps (upstream fork)
etl_2.8.2/                   ETL SQL scripts
openmrs-htmlformentry-module/         HTML form entry engine (upstream fork)
openmrs-htmlformentryui-module/       HTML form entry UI (upstream fork)
isanteplus-openmrs_15Dec2025/            Core iSantePlus module
openmrs-isanteplusreports-module_13Janv2026/     iSantePlus reports
openmrs-module-labintegration/        Lab integration
lib/maven-repo/        Vendored Maven dependencies (everest-core)
openmrs-module-mpi-client/            MPI client (OpenCR integration)
openmrs-module-outgoing-exception/    Outgoing message error handling
openmrs-referenceapp-module/  Reference application (upstream fork)
openmrs-module-registration/          Patient registration UI
openmrs-module-registrationcore/      Registration core (MPI import)
openmrs-module-xds-sender/            XDS.b document sender (SHR integration)
pom.xml                Root reactor POM (aggregator)
```

## Contributing

1. Create a feature branch from `main`
2. Make changes to the relevant module(s)
3. Verify the build: `mvn clean package -DskipTests -pl <changed-module> -am`
4. Open a pull request — CI will build and validate automatically
5. OMODs from your PR build are available as artifacts on the PR's Actions tab

## License

Individual modules retain their original licenses. Most modules are licensed under the [Mozilla Public License 2.0](https://www.mozilla.org/en-US/MPL/2.0/) or the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
