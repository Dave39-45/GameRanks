import { Component, OnInit } from '@angular/core';

import {DALForWritesService} from "../DAL/DALForWrites.service";
import {DisplayMessageService} from '../display-message.service';
import {IsLoggedInService} from "../is-logged-in.service";
import {RegUser} from "../models/entity/RegUser";

@Component({
	selector: 'app-menu',
	templateUrl: './menu.component.html',
	styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
	loginOpen = false;

	elements = {
		loginC: null,
		loginInnerC: null,
		username: null,
		password: null,

		logout: null,

		messageC: null
	};

	constructor(private DALForWrites: DALForWritesService, private displayMessageService: DisplayMessageService, private isLoggedInService: IsLoggedInService) { }

	ngOnInit() {
		this.elements.loginC = document.getElementById("loginC");
		this.elements.loginInnerC = document.getElementById("loginInnerC");
		this.elements.username = document.getElementById("loginUsername");
		this.elements.password = document.getElementById("loginPassword");

		this.elements.logout = document.getElementById("logout");

		this.elements.messageC = document.getElementById("messageC");
	}

	openLogin(){
		if(!this.loginOpen){
			this.loginOpen = true;
			this.elements.loginC.className = "openLogin";
			this.elements.loginInnerC.className = "openLoginInner";
		}
	}

	closeLogin(){
		if(this.loginOpen){
			this.loginOpen = false;
			this.elements.loginC.className = "closeLogin";
			this.elements.loginInnerC.className = "closeListFilter";
		}
	}

	login(){
		if(!this.isLoggedInService.isLoggedIn()){
			this.DALForWrites.login(new RegUser(this.elements.username.value, null, this.elements.password.value)).then(
				function(success){
					this.elements.loginC.style.display = "none";
					this.elements.logout.style.display = "block";

					this.isLoggedInService.login(success.user);
				}.bind(this),

				function(error){
					this.displayMessageService.displayMessage(error.error.message, true);
				}.bind(this)
			)
		}
	}

	logout(){
		if(this.isLoggedInService.isLoggedIn()){
			this.DALForWrites.logout().then(
				function(success){
					this.elements.logout.style.display = "none";
					this.elements.loginC.style.display = "block";

					this.isLoggedInService.logout();

					if(window.location.href.indexOf("user") !== -1){
						window.location.href = "/";
					}
				}.bind(this),

				function(error){
					this.displayMessageService.displayMessage(error.error.message, true);
				}.bind(this)
			)
		}
	}

	closeMessage(){
		this.elements.messageC.classList.remove("displayMessage");
	}
}
