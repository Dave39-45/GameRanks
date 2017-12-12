import { Injectable } from '@angular/core';
import {User} from "./models/entity/User";

@Injectable()
export class IsLoggedInService {
	private loggedIn: boolean;
	private user: User;

  	constructor() {
		this.loggedIn = false;
	}

	isLoggedIn(){
		return this.loggedIn;
	}

	getUsername(){
		return this.isLoggedIn() ? this.user.username : null;
	}

	setUser(user: User){
		this.user = user;
	}

	login(user: User){
		this.loggedIn = true;
		this.user = user;
	}

	logout(){
		this.loggedIn = false;
		this.user = null;
	}
}
