import {BaseModel} from "./BaseModel"
import {ReviewUser} from "./ReviewUser"

export class Review extends BaseModel{
	user: ReviewUser;

	score: number = 1;
	pros: string[] = [];
	cons: string[] = [];
	platform: string = "";
	reviewText: string = "";
	createdOn: string = "";

	constructor(id: number, version: number, user: ReviewUser, score: number, pros: string, cons: string, platform: string, reviewText: string, createdOn: string){
		super(id, version);
		this.user = user;
		this.score = score;
		this.pros = pros.split("#");
		this.cons = cons.split("#");
		this.platform = platform;
		this.reviewText = reviewText;
		this.createdOn = createdOn;
	}
}
