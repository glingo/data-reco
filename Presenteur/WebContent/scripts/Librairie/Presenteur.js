/**
 * Le presenteur est une API permettant de disposer des diapositives.
 * 
 * Celles ci sont ecrites en json.
 * 
 * 
 * TODO : permettre la génération de diapositives via des scripts php ou java.
 * 
 * NOTA ::
 * 
 * Dans des langages objets comme Java ou C++, 
 * this désigne au sein d'une classe l'instance de la classe sur laquelle on agit. 
 * Ce n'est pas le cas en JavaScript. 
 * En JavaScript, "this" désigne l'objet qui appelle 
 * la méthode dans laquelle il se trouve (oui il y a une subtile différence !). 
 */

(function(){
	
if (typeof lux != "undefined"){

	function Presentation(){
		
		this.nom = "Nouvelle presentation";  // le nom de la presentation.
		
		this.diapositives = [];				// le tableau de diapositives.
		
		this.jQueryDiapositives;
		
		this.current = 0;					// la diapositive en cours.
		
		this.ready = false;

	};
	
	Presentation.prototype = new Presentation();
	
	Presentation.prototype.display = function(selector){
		
		var screen = $('<section id="main">');
		
		$(this.jQueryDiapositives).each(function(index,objet){
			screen.append(objet.jQueryDom);
		});
		this.interval = clearInterval(this.interval);
		
		$('body').append(screen);
	};
	
	Presentation.prototype.load = function(){
		this.ready = false;
		
		function callLoad(obj,index){
			obj.build();
		};
		
		this.diapositives.each(callLoad);
		
		this.ready = true;
		
		return this;
	};

	Presentation.prototype.slide = function(){
		var jQueryDiapos = $(this.jQueryDiapositives);
		try{
			if(this.current + 1 < jQueryDiapos.length){
				jQueryDiapos.each(function(index,maDiapo){
					switch(index){
						case lux.PRESENTEUR.onAir.current + 2:
								maDiapo.move("pool","future");
							break;
						
						case lux.PRESENTEUR.onAir.current + 1:
								maDiapo.move("future","current");
							break;
					
						case lux.PRESENTEUR.onAir.current:
								maDiapo.move("current","before");
							break;
							
						case lux.PRESENTEUR.onAir.current - 1:
								maDiapo.move("before","pool");
							break;
					}
				});
				this.current++;
				console.log("slide","current",this.current,jQueryDiapos.length);
			}
		}catch(exception){
			console.log(exception);
		}
	};
	
	Presentation.prototype.unSlide = function(){
		var jQueryDiapos = $(this.jQueryDiapositives);
		try{
			if(this.current - 1 >= 0){
				jQueryDiapos.each(function(index,maDiapo){
					switch(index){
						case lux.PRESENTEUR.onAir.current + 1:
							maDiapo.move("future","pool");
							break;
					
						case lux.PRESENTEUR.onAir.current:
							maDiapo.move("current","future");
							break;
							
						case lux.PRESENTEUR.onAir.current - 1:
								maDiapo.move("before","current");
							break;
						
						case lux.PRESENTEUR.onAir.current - 2:
							maDiapo.move("pool","before");
							break;
					}
				});
				this.current--;
				console.log("unSlide","current",this.current);
			}
		}catch(exception){
			console.log(exception);
		}
	};

	Presentation.prototype.fromJSON = function(json){
		
		this.nom = json.nom;
		
		this.diapositives = json.diapositives;
		
		return this;
	};
	
	
	/**
	 * 
	 */
	function Diapositive(){
		
		this.presentation; 	// elle appartient a une presentation.
		this.ordre;			// Elle a une place dans la presentation.
		this.titre;			// elle a un titre.
		this.section;		// elle a un contenu.
		
		this.jQueryDom;		// Sa representation DOM jQuery
		this.dom;			// Sa representation en dom js
		
		return this;
	};

	/**
	 * La function build, va mettre construire le dom et le stocker dans l'objet.
	 * 
	 * Une section et un titre par defaut, et un contenu.
	 */
	Diapositive.prototype.build = function(){
		
		// la section diapo.
		var section = $('<section></section>');
		section.addClass("diapo");
		section.attr("id",this.ordre);
		switch (this.ordre){
			case 1:
				section.addClass("current");
				break;
			case 2 :
				section.addClass("future");
				break;
			default :
				section.addClass("pool");
				break;
		}
		
		var middle = $('<section>');
		
		middle.addClass("middle");
		
		// le titre.
		var h2 = $("<header><h2>"+this.titre+"</h2><header>");
		middle.append(h2);
		
		// le corps de la diapo.
		for(var prop in this.section){
			var valeur = this.section[prop];
			var el = $("<"+prop+">");
			// on parse le type de l'element pour des traitements ou des appels particuliers.
			switch(prop){
				case 'text' : 
					el.append(valeur);
					break;
				case 'pre' : 
					el.append(valeur.join("\n"));
					break;
				case 'section' : 
					el.append(valeur.join("\n"));
					break;
					
				default :
					el.html(valeur);
					break;
			}
			middle.append(el);
		}

		this.jQueryDom = section;
		section.append(middle);
		
		$("#contents").append(section);
		
	};
	
	Diapositive.prototype.move = function(from,to){
		this.jQueryDom.removeClass("pool");
		this.jQueryDom.removeClass(from);
		this.jQueryDom.addClass(to);
	};

	/**
	 * Un constructeur depuis un json.
	 * @param json
	 */
	Diapositive.prototype.fromJSON = function (json){
		
		this.presentation = json.presentation;
		this.ordre = json.ordre;
		this.titre = json.titre;
		this.section = json.section;
		
		this.build();
		
		return this;
	};
	
	var Presenteur = {
			
			Developpeur : "Florian",
			
			onAir : new Presentation(),
			
			load : function(configURL){
				console.log("load",configURL);
				
				function chargerDiapos(config){
					function parse(index, objet){
						var maDiapo = new Diapositive();
						var urlFolder = config.folder;
						// TODO faire des verification sur l'objet.
						$.getJSON(urlFolder+objet,function(data,json){
							maDiapo.fromJSON(data);
						})
						.done()
						.error();
						maDiapo.presentation = lux.PRESENTEUR.onAir;
						return maDiapo;
					};
					lux.PRESENTEUR.onAir.jQueryDiapositives = $(lux.PRESENTEUR.onAir.diapositives).map(parse);
				};
				
				function chargerPresentation(config){
					// Nous chargons une presentation.
					if(config){
						lux.PRESENTEUR.onAir.fromJSON(config);
						chargerDiapos(config);
					}
				};
				
				$.getJSON(configURL,chargerPresentation)
				.done(function(){
					lux.PRESENTEUR.onAir.ready = true;
				})
				.error();
				
				function wait(){
					if(lux.PRESENTEUR.onAir.ready){
						lux.PRESENTEUR.onAir.interval = clearInterval(lux.PRESENTEUR.onAir.interval);
						if(!lux.PRESENTEUR.onAir.interval){
							lux.PRESENTEUR.onAir.display();
							// on prend la priorité sur les evenements claviers
							//TODO voir une autre soluce
							$(document).keypress(function(e) {
								e.preventDefault();
								console.log(e);
								if(e.which == 32){
									lux.PRESENTEUR.onAir.slide();
								}if(e.which == 98){
									lux.PRESENTEUR.onAir.unSlide();
								}});
						}
					}
				};
				
				lux.PRESENTEUR.onAir.interval = setInterval(wait,300);
			}
	};
	
	if(!this.lux.PRESENTEUR){this.lux.PRESENTEUR = Presenteur;};
}})();