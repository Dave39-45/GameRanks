import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router'

import {UserPageStruct} from "../models/serverStructs/page/UserPageStruct";
import {DALForReadsService} from "../DAL/DALForReads.service";
import {DALForWritesService} from "../DAL/DALForWrites.service";
import {DisplayMessageService} from "../display-message.service";
import {IsLoggedInService} from "../is-logged-in.service";
import {ResponseToModelService} from "../ResponseToModel.service";
import {User} from "../models/entity/User";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
	userPageStruct: UserPageStruct = new UserPageStruct(new User(0, 0, "", "", "", ""), []);

	constructor(private DALForReads: DALForReadsService, private DALForWrites: DALForWritesService, private displayMessageService: DisplayMessageService,
				private isLoggedInService: IsLoggedInService, private responseToModelService: ResponseToModelService, private router: Router) {
		if(!isLoggedInService.isLoggedIn()){
			this.router.navigateByUrl("/");
		}
	}

	async ngOnInit() {
		window.scrollTo(0, 0);

		await this.DALForReads.getUserPageData().then(
			function(result){
				this.userPageStruct = this.responseToModelService.convertUserPageData(result);

				this.UserHandler.init(this);
				this.ReviewHandler.init(this);
				this.ConfirmHandler.init();
			}.bind(this),

			function(error){
				if(error.status = 401){
					this.router.navigateByUrl("/");
					this.displayMessageService.displayMessage("You have to login!", true);
				}
			}.bind(this)
		);
	}

	UserHandler = {
		parentThis: null,
		settingsOpen: false,

		elements: {
			userAvatar: null,
			settingsOpener: null,
			settings: null,

			avatarImg: null,
			avatarInput: null,
			passwordInput: null,
			passwordAgainInput: null,
			emailInput: null
		},

		init(parentThis){
			this.parentThis = parentThis;

			this.elements.userAvatar = document.getElementById("userAvatar");
			this.elements.settingsOpener = document.getElementById("userSettingsOpener");
			this.elements.settings = document.getElementById("userSettings");

			this.elements.avatarImg = document.getElementById("userSettingsAvatarImg");
			this.elements.avatarInput = document.getElementById("userSettingsAvatar");
			this.elements.passwordInput = document.getElementById("userSettingsNewPsw");
			this.elements.passwordAgainInput = document.getElementById("userSettingsNewPswA");
			this.elements.emailInput = document.getElementById("userSettingsNewEmail");
		},

		userSettingsOpenerClicked(){
			if(!this.settingsOpen){
				this.settingsOpen = true;
				this.elements.settingsOpener.classList.add("listFilterOpenerOn");
				this.elements.settings.className = "openUserSettings";
			}
			else{
				this.settingsOpen = false;
				this.elements.settingsOpener.classList.remove("listFilterOpenerOn");
				this.elements.settings.className = "closeUserSettings";
			}
		},

		avatarChanged(){
			if(this.elements.avatarInput.value === ""){
				this.elements.avatarImg.src = this.parentThis.userPageStruct.user.avatar;
			}
			else{
				this.elements.avatarImg.src = this.elements.avatarInput.value;
			}
		},

		async changeUserData(){
			var avatar = this.elements.avatarInput.value,
				password = this.elements.passwordInput.value,
				passwordAgain = this.elements.passwordAgainInput.value,
				email = this.elements.emailInput.value;

			if(avatar !== "" || (password !== "" && passwordAgain !== "") || email !== ""){
				if(this.validateUserData()){
					//Mindent egyben vegrehajtunk es a vegen attol fuggoen, hogy mi lett ugy jelenitjuk meg az uzenetet.
					var successMessage = "Successful change: ",
						errorMessage = "";

					if(avatar !== ""){
						await this.parentThis.DALForWrites.changeAvatar(avatar).then(
							function(success){
								successMessage += "avatar, ";

								this.parentThis.userPageStruct.user = success.user;
								this.parentThis.isLoggedInService.setUser(success.user);
							}.bind(this),

							function(error){
								errorMessage += error.message;
							}.bind(this)
						)
					}

					if(password !== ""){
						await this.parentThis.DALForWrites.changePassword(password, passwordAgain).then(
							function(success){
								successMessage += "password, ";
								this.elements.passwordInput.value = "";
								this.elements.passwordAgainInput.value = "";

								this.parentThis.userPageStruct.user = success.user;
								this.parentThis.isLoggedInService.setUser(success.user);
							}.bind(this),

							function(error){
								console.log(error);
								errorMessage += " " + error.message;
							}.bind(this)
						)
					}

					if(email !== ""){
						await this.parentThis.DALForWrites.changeEmail(email).then(
							function(success){
								successMessage += "email, ";
								this.elements.emailInput.value = "";

								this.parentThis.userPageStruct.user = success.user;
								this.parentThis.isLoggedInService.setUser(success.user);
							}.bind(this),

							function(error){
								errorMessage += " " + error.message;
							}.bind(this)
						)
					}

					if(successMessage.slice(-2) === ", "){
						successMessage = successMessage.slice(0, -2);
					}

					//Ha nincs hiba
					if(errorMessage === ""){
						this.parentThis.displayMessageService.displayMessage(successMessage, false);
					}
					//Ha van hiba
					else{
						//Ha csak hiba van
						if(successMessage === "Successful change: "){
							this.parentThis.displayMessageService.displayMessage(errorMessage, true);
						}
						//Ha siker es hiba is van
						else{
							this.parentThis.displayMessageService.displayMessage(successMessage + errorMessage, null);
						}
					}
				}
			}
		},

		validateUserData(){
			if(this.elements.avatarInput.value === this.parentThis.userPageStruct.user.avatar){
				this.parentThis.displayMessageService.displayMessage("Your avatar is the same!", true);
				return false;
			}
			if(this.elements.passwordInput.value !== this.elements.passwordAgainInput.value){
				this.parentThis.displayMessageService.displayMessage("The passwords must be the same!", true);
				return false;
			}
			else if(this.elements.emailInput.value !== "" && !this.elements.emailInput.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)){
				this.parentThis.displayMessageService.displayMessage("You need a valid email!", true);
				return false;
			}

			return true;
		}
	};



	ReviewHandler = {
		parentThis: null,

		filterOpen: false,
		newReviewOpen: false,

		elements: {
			filterOpener: null,
			filter: null,
			filterGame: null,
			filterScore: null,
			filterOrder: null
		},

		reviews: null,
		filteredReviews: null,

		init(parentThis){
			this.parentThis = parentThis;

			this.reviews = this.filteredReviews = parentThis.userPageStruct.userPageReviewStructs;

			this.elements.filterOpener = document.getElementById("reviewFilterOpener");
			this.elements.filter = document.getElementById("listFilter");
			this.elements.filterGame = document.getElementById("reviewFilterGame");
			this.elements.filterScore = document.getElementById("reviewFilterScore");
			this.elements.filterOrder = document.getElementById("reviewFilterOrder");

			this.filterReview();
		},

		filterOpenerClicked(){
			if(!this.filterOpen){
				this.filterOpen = true;
				this.elements.filterOpener.classList.add("listFilterOpenerOn");
				this.elements.filter.className = "openListFilter";
			}
			else{
				this.filterOpen = false;
				this.elements.filterOpener.classList.remove("listFilterOpenerOn");
				this.elements.filter.className = "closeListFilter";
			}
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
			var name = this.elements.filterGame.value.toLowerCase(),
				score = this.elements.filterScore.options[this.elements.filterScore.selectedIndex].text,
				order = this.elements.filterOrder.options[this.elements.filterOrder.selectedIndex].text,
				filteredArr = this.reviews;

			if(order === "Decreasing"){
				filteredArr.sort(function(a, b) {
					return b.review.score - a.review.score;
				});
			}
			else if(order === "Increasing"){
				filteredArr.sort(function(a, b) {
					return a.review.score - b.review.score;
				});
			}

			var dontCheckName = name === "",
				dontCheckScore = score === "All",
				scoreVal = Number(score);

			filteredArr = this.reviews.filter(reviewStruct => ((dontCheckName || reviewStruct.gameName.toLowerCase().indexOf(name) !== -1) && (dontCheckScore || reviewStruct.review.score === scoreVal)));

			this.filteredReviews = filteredArr;
		},




		editReview(reviewId: number){
			var struct = this.parentThis.userPageStruct.userPageReviewStructs.find(struct => struct.review.id === reviewId);

			//Informaljuk a game oldalt, hogy jelenitse meg a review -t
			window.localStorage.setItem("reviewToEditId", "" + reviewId);
			this.parentThis.router.navigateByUrl("/game/" + struct.gameId);
		},

		confirmDelete(reviewId: number){
			this.parentThis.ConfirmHandler.displayConfirmation("Are you sure, you want to delete the review?", this.deleteReview, this, [reviewId]);
		},

		async deleteReview(reviewId: number){
			var review = this.reviews.find(function(r){return r.review.id === reviewId;});

			await this.parentThis.DALForWrites.deleteReview(review.gameId).then(
				function(result){
					var index = this.parentThis.userPageStruct.userPageReviewStructs.findIndex(struct => struct.review.id === reviewId);

					if(index >= 0){
						this.parentThis.userPageStruct.userPageReviewStructs.splice(index, 1);
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
