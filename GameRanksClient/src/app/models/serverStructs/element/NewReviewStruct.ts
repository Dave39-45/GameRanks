import {Review} from "../../entity/Review";

export class NewReviewStruct{
	review: Review;
	message: string;

	constructor(review: Review, message: string){
		this.review = review;
		this.message = message;
	}
}
