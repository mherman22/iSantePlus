/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.isanteplusreports;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.isanteplusreports.api.IsantePlusReportsService;
import org.openmrs.module.metadatadeploy.api.MetadataDeployService;
import org.openmrs.module.metadatadeploy.bundle.MetadataBundle;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.scheduler.SchedulerException;
import org.openmrs.scheduler.Task;
import org.openmrs.scheduler.TaskDefinition;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class IsantePlusReportsActivator extends BaseModuleActivator {

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * @see #started()
     */
    public void started() {
        log.info("Started IsantePlusReports");
        RegisterReports register = new RegisterReports();
        AppFrameworkService appFrameworkService = Context.getService(AppFrameworkService.class);

        try {
            MetadataDeployService metadataDeployService = Context.getService(MetadataDeployService.class);
            register.registerReports();
            registerTask("Clean reports request iSantePlus", "Clean Reports Request for iSantePlus", RegisterReportsTask.class,
                    60 * 60 * 24l);
            returnIpAddress();
            appFrameworkService.disableApp("reportingui.reports");
            Context.getService(IsantePlusReportsService.class).setEventScheduler();
            installReportsRoleAndPrivilege(metadataDeployService);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @see #shutdown()
     */
    public void shutdown() {
        log.info("Shutdown IsantePlusReports");
    }

    private boolean registerTask(String name, String description, Class<? extends Task> clazz, long interval) {
        try {
            Context.addProxyPrivilege("Manage Scheduler");

            TaskDefinition taskDef = Context.getSchedulerService().getTaskByName(name);
            if (taskDef == null) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 20);
                taskDef = new TaskDefinition();
                taskDef.setTaskClass(clazz.getCanonicalName());
                taskDef.setStartOnStartup(true);
                taskDef.setRepeatInterval(interval);
                taskDef.setStarted(true);
                taskDef.setStartTime(cal.getTime());
                taskDef.setName(name);
                taskDef.setUuid(UUID.randomUUID().toString());
                taskDef.setDescription(description);
                Context.getSchedulerService().scheduleTask(taskDef);
            }
        } catch (SchedulerException ex) {
            log.warn("Unable to clean reports request '" + name + "' with scheduler", ex);
            return false;
        } finally {
            Context.removeProxyPrivilege("Manage Scheduler");
        }
        return true;
    }

    public void returnIpAddress() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String newIp = ip.getHostAddress().toString();
        String oldIp = Context.getAdministrationService().getGlobalProperty("iSantePlusReports.ipAddress");
        if (!newIp.equals(oldIp)) {
            Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("iSantePlusReports.ipAddress", newIp));
        }
    }

    private void installReportsRoleAndPrivilege(MetadataDeployService service) {

        // MetadataDeployService deployService = Context.getService(MetadataDeployService.class);
        // MetadataBundle reportsRolePrivilegeMetadata = Context.getRegisteredComponent("reportsRolePrivilegeMetadata", AddRoleAndPrivilegeBundle.class);
        MetadataBundle reportsRolePrivilegeMetadata = Context.getRegisteredComponent("reportsRolePrivilegeMetadata", MetadataBundle.class);
        service.installBundle(reportsRolePrivilegeMetadata);
    }

}
