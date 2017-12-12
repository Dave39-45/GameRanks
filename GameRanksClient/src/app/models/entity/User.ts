import {AccessLevel} from "../../enums/AccessLevel";
import {BaseModel} from "./BaseModel";

export class User extends BaseModel{
	username: string;
	email: string;
	avatar: string;
	accessLevel: AccessLevel;

	constructor(id: number, version: number, username: string, email: string, avatar: string, accessLevel: string){
		super(id, version);
		this.username = username;
		this.email = email;
		this.avatar = avatar;
		this.accessLevel = AccessLevel[accessLevel];
	}
}
