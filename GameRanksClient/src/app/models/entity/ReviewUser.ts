import {BaseModel} from "./BaseModel"

export class ReviewUser extends BaseModel{
	username: string;
	avatar: string;

	constructor(username: string, avatar: string, id?: number, version?: number){
		super(id, version);
		this.username = username;
		this.avatar = avatar;
	}
}
