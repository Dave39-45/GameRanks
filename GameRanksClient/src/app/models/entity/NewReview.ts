export class NewReview {
	score: number;
	pros: string;
	cons: string;
	platform: string;
	reviewText: string = "";

	constructor(score: number, pros: string, cons: string, platform: string, reviewText: string){
		this.score = score;
		this.pros = pros;
		this.cons = cons;
		this.platform = platform;
		this.reviewText = reviewText;
	}
}
