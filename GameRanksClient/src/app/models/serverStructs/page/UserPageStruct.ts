import {User} from "../../entity/User";
import {UserPageReviewStruct} from "../../entity/UserPageReviewStruct";

export class UserPageStruct{
	user: User;
	userPageReviewStructs: UserPageReviewStruct[];

	constructor(user: User, userPageReviewStructs: UserPageReviewStruct[]){
		this.user = user;
		this.userPageReviewStructs = userPageReviewStructs;
	}
}
