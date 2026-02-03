package org.openmrs.module.isanteplusreports;

import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

@Component("reportsRolePrivilegeMetadata")
public class AddRoleAndPrivilegeBundle extends AbstractMetadataBundle {

	 public static class _Privilege {
	        public static final String REPORTS_PRIVILEGE = "App: reportingui.reports"; 
	        public static final String VIEW_OUTGOING_MESSAGE_PRIVILEGE = "App: View Outgoing Messages"; 
	    }

	    public static class _Role {
	    		public static final String REPORTS_ROLES = "Application: View Reports";
	    		public static final String OUTGOING_MESSAGE_ROLES = "Application: View Outgoing Messages";
	    }

	
	@Override
	public void install() throws Exception {
		// TODO Auto-generated method stub
		//if(!Context.hasPrivilege("App: reportingui.reports")){
			install(privilege(_Privilege.REPORTS_PRIVILEGE, "Able to access reports"));
       // }
		
		//if(Context.getUserService().getRole("Application: View Reports") == null){
			install(role(_Role.REPORTS_ROLES, "Able to view reports", idSet(), idSet(
	                _Privilege.REPORTS_PRIVILEGE)));
		//}
		
		//if(!Context.hasPrivilege("View Outgoing Messages")){
			
			//install(privilege(_Privilege.VIEW_OUTGOING_MESSAGE_PRIVILEGE, "Ability to View Outgoing Messages"));
			
        //}
		
		//if(Context.getUserService().getRole("Application: View Outgoing Messages") == null){
			install(role(_Role.OUTGOING_MESSAGE_ROLES, "Able to view Outgoing Messages", idSet(), idSet(
	                _Privilege.VIEW_OUTGOING_MESSAGE_PRIVILEGE)));	
		//}
		
	}

}
