(function(){
	console.log("Chargement en cours",document);
	
	var slider = {
			presentations : {
								liste : [],
								
								integrer : function(json){
									if(json){
										PRESENTEUR.presentations.liste.push(json);
									}
								},
								
								charger : function(pres){
									if(pres){
										for(var i in PRESENTEUR.presentations.liste){
											if(PRESENTEUR.presentations.liste[i].nom == pres){
												PRESENTEUR.chargerDiapos(PRESENTEUR.presentations.liste[i].prefix);
											}
										}
									}
								},
								
								menu : function(){
									var section = document.createElement("section");
									section.className = "diapo";
									section.id = "menu";
									var ul = document.createElement("ul");
									ul.className = "ul-menu";
									for(var i in PRESENTEUR.presentations.liste){
										$("#main").find("span").remove();
										var li = document.createElement("li");
										li.innerHTML = PRESENTEUR.presentations.liste[i].nom;
										li.className = "li-menu";
										li.linkable = i;
										li.onclick = function(ev){
											PRESENTEUR.chargerDiapos(this.linkable);
											console.log(PRESENTEUR.presentations);
											PRESENTEUR.presentations.display();
										};

										ul.appendChild(li);
										$(section).append(ul);
									};
									return $(section);
								},
								
								display : function(){
									$("#menu").remove();
									for(var i in PRESENTEUR.diapos){
										$("#main").append(PRESENTEUR.diapos[i].dom);
									}
									$(document).keypress(function(e) {
										console.log(e);
										if(e.which == 32){
											PRESENTEUR.slide("+");
										}if(e.which == 98){
											PRESENTEUR.slide("-");
										}});
								}
							},
			
			diapos : [],
			
			current : 0,
			
			charger : function(data){
				if(data){
					for(var i in data){
						PRESENTEUR.presentations.integrer(data[i]);
					}
				}
				return this;
			},
			
			organise : function(obj){
				
				obj.removeClass("before");
				obj.removeClass("current");
				obj.removeClass("future");
				obj.removeClass("pool");
				
				var classe = "pool";
				
				switch(Number(obj.attr("id"))){
					case this.current - 1 :
						classe = "before";
						break;
						
					case this.current :
						classe = "current";
						break;
						
					case this.current + 1:
						classe = "future";
						break;
						
					default :
						classe = "pool";
						break;
				}
				
				obj.addClass("diapo").addClass(classe);
				
			},
			
			chargerDiapos : function(prefix){
				console.log("integration de diapos");
				
				var cast = function(index,obj){
					function cast(obj){
						
						console.log(obj);
						$(index).remove();
						
						
						
//						var section = document.createElement("section");
//						section.id = index;
//						section.innerHTML = obj.contenu;
//						PRESENTEUR.organise($(section));
//						obj.dom = $(section);
						return obj;
					};

					$.getJSON('json/presentations.json', cast).done(function(){});
				
					PRESENTEUR.diapos
					$("#main").append(make(obj));
				};
				
				$(PRESENTEUR.diapos).each(cast);
				
			},
			
			slide : function(operator){
				this.current = eval(this.current + operator + 1);
				if(this.current >= 0){
					if(this.current <= this.diapos.length){
						for(var i= 0; i< this.diapos.length; i++){
							this.organise($("#"+i));
						}
					}else{
						this.current--;
					}
				}else{
					this.current++;
				}
				return this;
			},
			
			initialiser : function(){

				$.getJSON('json/presentations.json', PRESENTEUR.charger).done(function(){$("#main").append(PRESENTEUR.presentations.menu);});
			}
	};
	onload = function(){
		if(!window.PRESENTEUR){window.PRESENTEUR = slider;};
		PRESENTEUR.initialiser();
	};
})();