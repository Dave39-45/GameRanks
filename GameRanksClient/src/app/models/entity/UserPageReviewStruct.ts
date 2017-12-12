import {Review} from "./Review";

export class UserPageReviewStruct{
	review: Review;
	gameId: number;
	gameName: string;
	gameImage: string;

	constructor(review: Review, gameId: number, gameName: string, gameImage: string){
		this.review = review;
		this.gameId = gameId;
		this.gameName = gameName;
		this.gameImage = gameImage;
	}
}
