import { Component, OnInit } from '@angular/core';

import {DALForWritesService} from "../DAL/DALForWrites.service";
import {DisplayMessageService} from "../display-message.service";
import {RegUser} from "../models/entity/RegUser";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
	elements = {
		username: null,
		email: null,
		password: null,
		passwordA: null
	};

	constructor(private DALForWrites: DALForWritesService, private displayMessageService: DisplayMessageService) {}

	ngOnInit() {
	  	window.scrollTo(0, 0);

		this.elements.username = document.getElementById("regUsername");
		this.elements.email = document.getElementById("regEmail");
		this.elements.password = document.getElementById("regPassword");
		this.elements.passwordA = document.getElementById("regPasswordAgain");
	}

	register(){
		if(this.validate()){
			this.DALForWrites.register(new RegUser(this.elements.username.value, this.elements.email.value, this.elements.password.value), this.elements.passwordA.value).then(
				function(success){
					this.displayMessageService.displayMessage(success.message, false);

					setTimeout(function(){
						this.clearInputs();
						window.location.href = "/";
					}.bind(this), 1500);
				}.bind(this),

				function(error){
					this.displayMessageService.displayMessage(error.error.message, true);
				}.bind(this)
			)
		}
	}

	validate(){
		if(this.elements.username.value === ""){
			this.displayMessageService.displayMessage("You need a username!", true);
			return false;
		}
		if(this.elements.email.value === "" || !this.elements.email.value.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)){
			this.displayMessageService.displayMessage("You need a valid email!", true);
			return false;
		}
		else if(this.elements.password.value === ""){
			this.displayMessageService.displayMessage("You need a password!", true);
			return false;
		}
		else if(this.elements.passwordA.value === ""){
			this.displayMessageService.displayMessage("Repeat your password.", true);
			return false;
		}
		else if(this.elements.password.value !== this.elements.passwordA.value){
			this.displayMessageService.displayMessage("The passwords must be the same!", true);
			return false;
		}

		return true;
	}

	clearInputs(){
		this.elements.username.value = "";
		this.elements.email.value = "";
		this.elements.password.value = "";
		this.elements.passwordA.value = "";
	}
}
