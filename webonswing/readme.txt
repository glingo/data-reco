25/11/2004 - WebOnSwing Web Application Framework version 1.0.2 Release 
------------------------------------------------------------------

Changes from 1.0.1 beta2 to 1.0.2 Release --- (24/06/2004 - 25/11/2004)
* Add DynApi support
* SharedContext -> SessionContext
* Add application context
* Add ProgressBarUIContributor
* Add SliderUIContributor
* Add SpinnerUIContributor
* Add InternalFrameUIContributor
* Add DesktopUIContributor
* Add Table2UIContributor (DynApi)
* Refactoring: SharedContextDefaultHtmlPage -> SaveContextDefaultHtmlPage
* JValidator is now a concrete class to perform custom valitations
* Fix some problems with refresh components in mozilla
* Add notification when cannot find some config resource
* Put template cache configuration in template manager xml
* Put page place and default in page manager xml
* Add a simple skin manager and skin class


Changes from 1.0 beta to 1.0.1 beta2 --- (05/04/2004 - 24/06/2004) 

* Use of Enabled property in validation components.
* Add DataTypeCheck operation to JCompareValidator.
* Fix some validation components to allow WebOnSwing running in JDK 1.3
* Remove dependency with Castor XML and xerces for configurations, now use XStream serialization library.
* Minor changes in xml configuration files.
* Remove Managers UI from main jar.
* Add valitation for JRadioButtons.
* Fix problems with FinishListener in javascript.
* Fix ListUIContributor when works with no DefaultListModel.
* Fix JRequiredField for JComboBox validations.
* JCheckBox and JRadioButton renderers now use "label" tag.
* Fix a bug with string comparison in javascript for JCompareValidator.
* Tasks description added in build.xml
* Some js classes were extracted from WebOnSwing.js to JList.js and JTabbedPane.js
* LabelUIContributor add id attribute to span and img tags.
* Refactoring: net.ar.webonswing.components -> net.ar.webonswing.wrapping
* Refactoring: All swing specific wrapping classes were grouped into net.ar.webonswing.wrapping.Swing
* Refactoring: net.ar.webonswing.html -> net.ar.webonswing.resources.html


Changes from 0.98.5 beta to 1.0 beta --- (2003 - 05/04/2004) 
*	Validation components: JRequiredValidator, JCompareValidator, JRangeValidator, JRegularExpressionValidator, JGroupValidator.
*	Fix bug in PropagateTemplateLayoutByName setting position and dimensions to zero for each processed component.
*	Method "assignContributor(...)" added to WosFramework.
*	Management of page exceptions thought web.xml.
*	SimpleResourceManager for resources that are inside WebOnSwing.jar or a any other "classpath resource".
*	JRootPane contributor assigment.
*	Change the automatic name generation from ababa.JButton01 to ababa_JButton01 for javascript handling.
*	KeyPositionSwingTemplate for templating in desktop application.
*	Fix a bug in template manager that clear predefined templates when cache is disable.
*	Fix a bug in PropagateTemplateLayoutByName when components dont have dimensions 





Intalling WebOnSwing [Demo | WebLog | WebApp]
---------------------------------------
Required files:
	WebOnSwing[Demo | Weblog | WebApp].war
	WebOnSwingBoot.jar
	
Running the demos:
	Before you put 'WebOnSwing[Demo | Weblog | WebApp].war' in webapps directory (or deploy this file using some tool) you 
	have to add 'WebOnSwingBoot.jar' library to boot classpath of your webserver, it depends on the webserver you are working 
	on, some requires the parameter '-Xbootclasspath/a:"WebOnSwingBoot.jar"' when startup the vm, others to copy this file 
	into a directory of the server classpath, or use some console to setup this.
	
	
