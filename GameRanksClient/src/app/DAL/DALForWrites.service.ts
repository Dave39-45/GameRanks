import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import {RegUser} from "../models/entity/RegUser";
import {UserStruct} from "../models/serverStructs/element/UserStruct";
import {NewReviewStruct} from "../models/serverStructs/element/NewReviewStruct";
import {NewReview} from "../models/entity/NewReview";
import {Review} from "../models/entity/Review";

@Injectable()
export class DALForWritesService {
	serverUrl: string = "/api/";//"http://localhost:8080/api/";

	httpHeader = {
		headers: new HttpHeaders({
			"Content-Type": "application/json"
		})
	};

	constructor(private http: HttpClient) { }

	login(user: RegUser): Promise<UserStruct>{
		return this.http.post<UserStruct>(this.serverUrl + "login", user, this.httpHeader).toPromise();
	}

	logout(): Promise<UserStruct>{
		return this.http.post<UserStruct>(this.serverUrl + "logout", {}, this.httpHeader).toPromise();
	}

	register(user: RegUser, passwordAgain: string): Promise<UserStruct>{
		return this.http.post<UserStruct>(this.serverUrl + "register?passwordAgain=" + passwordAgain, user, this.httpHeader).toPromise();
	}



	sendNewReview(gameId: string, review: NewReview): Promise<NewReviewStruct>{
		return this.http.post<NewReviewStruct>(this.serverUrl + "game/" + gameId, review, this.httpHeader).toPromise();
	}

	editReview(gameId: string, review: NewReview): Promise<NewReviewStruct>{
		return this.http.put<NewReviewStruct>(this.serverUrl + "game/" + gameId, review, this.httpHeader).toPromise();
	}

	deleteReview(gameId: string): Promise<Object>{
		return this.http.delete<Object>(this.serverUrl + "game/" + gameId, this.httpHeader).toPromise();
	}



	changeAvatar(avatar: string): Promise<UserStruct>{
		return this.http.put<UserStruct>(this.serverUrl + "user/changeAvatar?avatar=" + avatar, {}, this.httpHeader).toPromise();
	}

	changePassword(password: string, passwordAgain: string): Promise<UserStruct>{
		return this.http.put<UserStruct>(this.serverUrl + "user/changePassword?password=" + password + "&passwordAgain=" + passwordAgain, {}, this.httpHeader).toPromise();
	}

	changeEmail(email: string): Promise<UserStruct>{
		return this.http.put<UserStruct>(this.serverUrl + "user/changeEmail?email=" + email, {}, this.httpHeader).toPromise();
	}
}
