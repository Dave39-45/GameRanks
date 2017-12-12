import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DALForReadsService } from '../DAL/DALForReads.service';
import { DALForWritesService } from '../DAL/DALForWrites.service';
import { DisplayMessageService } from '../display-message.service';
import { ResponseToModelService } from "../ResponseToModel.service";
import { IsLoggedInService } from "../is-logged-in.service";
import { Platform } from '../enums/Platform';
import { Review } from '../models/entity/Review';
import { GameStruct } from "../models/serverStructs/element/GameStruct";
import {NewReview} from "../models/entity/NewReview";

@Component({
	selector: 'app-game',
	templateUrl: './game.component.html',
	styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {
	gameStruct: GameStruct;

	constructor(private DALForReads: DALForReadsService, private DALForWrites: DALForWritesService, private activatedRoute: ActivatedRoute,
				private displayMessageService: DisplayMessageService, private responseToModelService: ResponseToModelService, private isLoggedInService: IsLoggedInService) {

	}

	async ngOnInit() {
		window.scrollTo(0, 0);

		await this.DALForReads.getGamePageData(this.activatedRoute.snapshot.paramMap.get("id")).subscribe(function(result){
			this.gameStruct = this.responseToModelService.convertGamePageData(result);

			this.ReviewHandler.init(this);

			if(this.gameStruct.game.steamId){
				this.GalleryHandler.imgs = this.gameStruct.steamData.screenshots;
				this.GalleryHandler.init();
			}
		}.bind(this));

		this.ConfirmHandler.init();
	}

	GalleryHandler = {
		imgs: [],
		currImg: 0,
		nextTimeout: null,

		elements: {
			gallery: null
		},

		init(){
			this.elements.gallery = document.getElementById("gameGallery");

			this.elements.gallery.style.backgroundImage = "url('" + this.imgs[this.currImg].path_full + "')";
			this.nextTimeout = setTimeout(this.goRight.bind(this), 5000);
		},

		removeTimeout(){
			clearTimeout(this.nextTimeout);
		},

		goLeft(){
			if(this.imgs.length > 0){
				this.removeTimeout();
				--this.currImg;

				if(this.currImg < 0){
					this.currImg = this.imgs.length - 1;
				}

				this.elements.gallery.style.backgroundImage = "url('" + this.imgs[this.currImg].path_full + "')";
				this.nextTimeout = setTimeout(this.goRight.bind(this), 5000);
			}
		},

		goRight(){
			if(this.imgs.length > 0) {
				this.removeTimeout();
				++this.currImg;

				if (this.currImg === this.imgs.length) {
					this.currImg = 0;
				}

				this.elements.gallery.style.backgroundImage = "url('" + this.imgs[this.currImg].path_full + "')";
				this.nextTimeout = setTimeout(this.goRight.bind(this), 5000);
			}
		}
	};




	ReviewHandler = {
		parentThis: null,

		filterOpen: false,
		newReviewOpen: false,
		nextProNum: 2,
		nextConNum: 2,

		elements: {
			filterOpener: null,
			filter: null,
			filterName: null,
			filterScore: null,
			filterOrder: null,

			newReviewOpener: null,
			newReview: null,
			newReviewPros: null,
			newReviewCons: null,

			score: null,
			platform: null,
			text: null
		},

		reviews: null,
		filteredReviews: null,
		forEdit: false,

		init(parentThis){
			this.parentThis = parentThis;

			this.reviews = this.filteredReviews = parentThis.gameStruct.reviews;

			this.elements.filterOpener = document.getElementById("reviewFilterOpener");
			this.elements.filter = document.getElementById("listFilter");
			this.elements.filterName = document.getElementById("reviewFilterUser");
			this.elements.filterScore = document.getElementById("reviewFilterScore");
			this.elements.filterOrder = document.getElementById("reviewFilterOrder");

			this.elements.newReviewOpener = document.getElementById("newReviewOpener");
			this.elements.newReview = document.getElementById("newReview");
			this.elements.newReviewPros = document.getElementById("newReviewPros");
			this.elements.newReviewCons = document.getElementById("newReviewCons");

			this.elements.score = document.getElementById("reviewScore");
			this.elements.platform = document.getElementById("reviewPlatform");
			this.elements.text = document.getElementById("newReviewText");

			this.filterReview();

			//Megnezzuk, hogy a user oldal iranyitott e at, hogy szerkesszunk egy review -t
			if(window.localStorage.getItem("reviewToEditId")){
				this.editReview(Number(window.localStorage.getItem("reviewToEditId")));
				setTimeout(function(){
					document.getElementById("newReviewOpener").scrollIntoView({block: "start", inline: "nearest", behavior: "smooth"});
				}, 500);
				window.localStorage.removeItem("reviewToEditId");
			}
		},

		handleOpenClose(whichOne){
			if(whichOne === "f"){
				if(!this.filterOpen){
					if(this.newReviewOpen){
						this.closeNewReview();
					}

					this.openFilter();
				}
				else{
					this.closeFilter();
				}
			}
			else if(whichOne === "r"){
				if(!this.newReviewOpen){
					if(this.filterOpen){
						this.closeFilter();
					}

					this.openNewReview();
				}
				else{
					this.closeNewReview();
				}
			}
		},

		openFilter(){
			this.filterOpen = true;
			this.elements.filterOpener.classList.add("reviewOpenerOn");
			this.elements.filter.className = "openListFilter";
		},

		closeFilter(){
			this.filterOpen = false;
			this.elements.filterOpener.classList.remove("reviewOpenerOn");
			this.elements.filter.className = "closeListFilter";
		},

		openNewReview(){
			this.newReviewOpen = true;
			this.elements.newReviewOpener.classList.add("reviewOpenerOn");
			this.elements.newReview.className = "openNewReview";
		},

		closeNewReview(){
			this.newReviewOpen = false;
			this.elements.newReviewOpener.classList.remove("reviewOpenerOn");
			this.elements.newReview.className = "closeNewReview";
		},



		addNewProInput(){
			this.addNewProConInput(this.elements.newReviewPros, true);
		},

		addNewConInput(){
			this.addNewProConInput(this.elements.newReviewCons, false);
		},

		removeProInput($event){
			this.removeProConInput(this.elements.newReviewPros, "data-topro", $event.currentTarget.getAttribute("data-frompro"));
		},

		removeConInput($event){
			this.removeProConInput(this.elements.newReviewCons, "data-tocon", $event.currentTarget.getAttribute("data-fromcon"));
		},

		addNewProConInput(parent, isPro, optionalText){
			var container = document.createElement("div"),
				input = document.createElement("input"),
				remove = document.createElement("div");

			if(isPro){
				var id = "p" + this.nextProNum++,
					attrNamePlus = "pro",
					className = "pros";

				remove.onclick = this.removeProInput.bind(this);
			}
			else{
				id = "c" + this.nextConNum++;
				attrNamePlus = "con";
				className = "cons";

				remove.onclick = this.removeConInput.bind(this);
			}

			container.setAttribute("data-to" + attrNamePlus, id);
			input.setAttribute("type", "text");
			input.className = className;
			input.value = optionalText ? optionalText : "";
			remove.setAttribute("data-from" + attrNamePlus, id);

			container.appendChild(input);
			container.appendChild(remove);

			parent.appendChild(container);
		},

		removeProConInput(parent, attributeName, attributeValue){
			parent.removeChild(document.querySelector("[" + attributeName + "='" + attributeValue + "']"));
		},



		openCloseReview($event){
			var opener = $event.currentTarget.getAttribute("data-fromReview");

			if(opener){
				var reviewText = document.querySelector("[data-toReview='" + opener + "']");

				if(reviewText){
					if(reviewText.classList.contains("closeReview") || reviewText.classList.length === 0){
						$event.currentTarget.innerHTML = "ˆ";
						reviewText.className = "openReview";
					}
					else{
						$event.currentTarget.innerHTML = "ˇ";
						reviewText.className = "closeReview";
					}
				}
			}
		},



		filterReview(){
			var name = this.elements.filterName.value.toLowerCase(),
				score = this.elements.filterScore.options[this.elements.filterScore.selectedIndex].text,
				order = this.elements.filterOrder.options[this.elements.filterOrder.selectedIndex].text,
				filteredArr = this.reviews;

			if(order === "Decreasing"){
				filteredArr.sort(function(a, b) {
					return b.score - a.score;
				});
			}
			else if(order === "Increasing"){
				filteredArr.sort(function(a, b) {
					return a.score - b.score;
				});
			}

			var dontCheckName = name === "",
				dontCheckScore = score === "All",
				scoreVal = Number(score);

			filteredArr = this.reviews.filter(review => ((dontCheckName || review.user.username.toLowerCase().indexOf(name) !== -1) && (dontCheckScore || review.score === scoreVal)));

			this.filteredReviews = filteredArr;
		},

		clearReviewInput(){
			this.elements.newReviewPros.innerHTML = "";
			this.elements.newReviewCons.innerHTML = "";
			this.addNewProInput();
			this.addNewConInput();

			this.elements.text.innerHTML = "";
		},



		async submitReview(){
			var newReview = this.validateReview();

			if(newReview){
				if(this.parentThis.isLoggedInService.isLoggedIn()){
					//Ha uj ertekelest kuld
					if(!this.forEdit){
						await this.parentThis.DALForWrites.sendNewReview(this.parentThis.activatedRoute.snapshot.paramMap.get("id"), newReview).then(
							function(result){
								result.review = this.parentThis.responseToModelService.convertReview(result.review);

								this.parentThis.gameStruct.reviews.push(result.review);
								this.filterReview();

								this.parentThis.displayMessageService.displayMessage(result.message, false);

								this.closeNewReview();
								this.clearReviewInput();
							}.bind(this),

							function(error){
								this.parentThis.displayMessageService.displayMessage(error.error.message, true);
							}.bind(this)
						);
					}
					//Ha meglevot szerkeszt
					else{
						await this.parentThis.DALForWrites.editReview(this.parentThis.activatedRoute.snapshot.paramMap.get("id"), newReview).then(
							function(result){
								result.review = this.parentThis.responseToModelService.convertReview(result.review);

								var index = this.parentThis.gameStruct.reviews.findIndex(review => review.id === result.review.id);

								if(index >= 0){
									this.parentThis.gameStruct.reviews[index] = result.review;
									this.filterReview();

									this.parentThis.displayMessageService.displayMessage(result.message, false);

									this.closeNewReview();
									this.clearReviewInput();
								}
								else{
									this.parentThis.displayMessageService.displayMessage("Something happened. Try again!", true);
								}
							}.bind(this),

							function(error){
								this.parentThis.displayMessageService.displayMessage(error.error.message, true);
							}.bind(this)
						);
					}
				}
				else{
					this.parentThis.displayMessageService.displayMessage("You have to login before submitting the review.", true);
				}
			}
		},

		validateReview(): NewReview{
			var score = Number(this.elements.score.options[this.elements.score.selectedIndex].text),
				platform = this.elements.platform.options[this.elements.platform.selectedIndex].text,
				text = this.elements.text.value;

			if(isNaN(score) || score < 0 || score > 10){
				this.parentThis.displayMessageService.displayMessage("You have to set a valid score!", true);
				return null;
			}
			else if(!(platform in Platform)){
				this.parentThis.displayMessageService.displayMessage("You have to set a valid platform", true);
				return null;
			}
			else if(text.replace(/\s/g,'') === ""){
				this.parentThis.displayMessageService.displayMessage("You have to write a review!", true);
				return null;
			}

			var pros = document.getElementsByClassName("pros"),
				cons = document.getElementsByClassName("cons"),
				validPros:string[] = [],
				validCons:string[] = [];

			if(pros.length === 0 || cons.length === 0){
				this.parentThis.displayMessageService.displayMessage("You need at least one pro and con!", true);
				return null;
			}

			for(var i = 0; i<pros.length; ++i){
				var value = (<HTMLInputElement>pros[i]).value;

				if(value === ""){
					this.parentThis.displayMessageService.displayMessage("Pro is empty!", true);
					return null;
				}
				else{
					validPros.push(value);
				}
			}

			for(var i = 0; i<cons.length; ++i){
				var value = (<HTMLInputElement>cons[i]).value;

				if(value === ""){
					this.parentThis.displayMessageService.displayMessage("Con is empty!", true);
					return null;
				}
				else{
					validCons.push(value);
				}
			}

			return new NewReview(score, validPros.join("#"), validCons.join("#"), platform, text);
		},




		editReview(reviewId: number){
			var review: Review = this.reviews.find(function(r){return r.id === reviewId;});

			if(review){
				this.forEdit = true;
				this.addReviewToEditPanel(review);
				this.openNewReview();
				document.getElementById("newReviewOpener").scrollIntoView({block: "start", inline: "nearest", behavior: "smooth"});
			}
		},

		addReviewToEditPanel(review: Review){
			this.elements.score.value = review.score;
			this.elements.platform.value = review.platform;
			this.elements.text.value = review.reviewText;

			if(review.pros.length > 0){
				this.elements.newReviewPros.innerHTML = "";

				for(var pro of review.pros){
					this.addNewProConInput(this.elements.newReviewPros, true, pro);
				}
			}

			if(review.cons.length > 0){
				this.elements.newReviewCons.innerHTML = "";

				for(var con of review.cons){
					this.addNewProConInput(this.elements.newReviewCons, false, con);
				}
			}
		},

		confirmDelete(reviewId: number){
			this.parentThis.ConfirmHandler.displayConfirmation("Are you sure, you want to delete the review?", this.deleteReview, this, [reviewId]);
		},

		async deleteReview(reviewId: number){
			await this.parentThis.DALForWrites.deleteReview(this.parentThis.activatedRoute.snapshot.paramMap.get("id")).then(
				function(result){

					console.log(result);

					var index = this.parentThis.gameStruct.reviews.findIndex(review => review.id === reviewId);

					if(index >= 0){
						this.parentThis.gameStruct.reviews.splice(index, 1);
						this.filterReview();

						this.parentThis.displayMessageService.displayMessage(result.message, false);
					}
					else{
						this.parentThis.displayMessageService.displayMessage("Something happened. Try again!", true);
					}
				}.bind(this),

				function(error){
					this.parentThis.displayMessageService.displayMessage(error.error.message, true);
				}.bind(this)
			);
		}
	};




	ConfirmHandler = {
		yesHandler: null,
		context: this,
		args: [],

		elements: {
			container: null,
			message: null
		},

		init(){
			this.elements.container = document.getElementById("confirmC");
			this.elements.message = document.getElementById("confirmMessage");
		},

		displayConfirmation(message: string, handler, context, args){
			this.yesHandler = handler;
			this.context = context;
			this.args = args;
			this.elements.message.innerHTML = message;
			this.elements.container.classList.add("displayConfirm");
		},

		yes(){
			this.yesHandler.apply(this.context, this.args);
			this.no();
		},

		no(){
			this.elements.container.classList.remove("displayConfirm");
			this.yesHandler = null;
			this.context = this;
			this.args = [];
		}
	}
}
