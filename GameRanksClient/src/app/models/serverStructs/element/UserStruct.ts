import {User} from "../../entity/User";

export class UserStruct{
	user: User;
	message: string;

	constructor(user: User, message: string){
		this.user = user;
		this.message = message;
	}
}
