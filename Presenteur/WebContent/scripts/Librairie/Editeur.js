/**
 * 
 */
(function(){
if (typeof lux != "undefined"){
	
	var fromJSON = function(json){
		console.log(json);
		for(var propriete in json){
			console.log(this.hasOwnProperty(propriete),this,propriete);
			if(this.hasOwnProperty(propriete)){
				this[propriete] = json[propriete];
			}
		}
		this.build();
		
		return this;
	};
	
	function Action(json){
		this.libelle;
		this.img;
		this.id;
		this.jQueryDom;
		
		if(json){
			this.fromJSON(json);
		}
	}
	
	Action.prototype = new Action();
	
	Action.prototype.build = function(){

		var figure = $("<figure></figure>");
		
		var figcaption = $("figcaption");
		
		var img = $("<img/>");
		
		img.attr("id","monAction");
		
		img.attr("src","images/Presenteur.png");
		
		figure.append(img).append(figcaption);
	
		this.jQueryDom = figure;
		
		return this;
		
	};
	
	function Menu(){
		this.header = [];
		this.background = "";
		this.actions = [];
		this.jQueryDom;
	};
	
	Menu.prototype = new Menu();
	
	Menu.prototype.addAnAction = function(){
		
		if(!this.jQueryDom){
			this.build();
		}

		this.jQueryDom.append();
		
	};
	
	Menu.prototype.build = function(){
		var conteneur = $("<section></section>");
		
		conteneur.attr("id","actions").addClass("unDrop");
		
		var nav = $("<nav></nav>");
		
		// partie actions
		
		for(var action in this.actions){
			conteneur.append(action.build().jQueryDom);
		}
		
		this.jQueryDom = conteneur.append(nav);
		
		return this;
	};
	
	Menu.prototype.fromJSON = fromJSON;
	
	Action.prototype.fromJSON = fromJSON;
	
	var editeur = {
		
		menu : new Menu(),
			
		load : function(url){
			
			var monMenu = this.menu;
			
			function charger(data){
				monMenu.fromJSON(data);
			}
			
			$.getJSON(url,charger)
			.done(function(){
				console.log("done");
			});
			
			//console.log(monMenu);
		}
		
	};
		
		if(lux.PRESENTEUR){
			if(!lux.PRESENTEUR.Editeur){
				lux.PRESENTEUR.Editeur = editeur;
			}
		}
	
}})();