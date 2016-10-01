/**
 * 
 */
(function(){
	
	var demanderAuServeur = function(action,form,whatShouldIdo,onDone){
		jQuery.get(action,form,function(data){whatShouldIdo("whatShouldIdo",data);}).done(onDone);
	};
	
	var MonObjetTest = {
			jQueryIsAvailable : (function(){
				if($ && jQuery){
					return true;
				}else{
					return false;
				}
			})(),
		
			premierTest : function(){
				var monFormulaire = {
					monPremierParametre : "test"	
				};
				demanderAuServeur("IndexServlet",monFormulaire,console.log,console.log);
			},
			
			deuxiemeTest : function(){
				console.log("deuxieme test");
			}
	};

	console.log("jQuery est il available", this.jQueryIsAvailable);
	MonObjetTest.premierTest();
	
})();