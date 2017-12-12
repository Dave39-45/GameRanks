export class StatisticStruct{
	numberOfGamesPerGenre: Object[][];
	numberOfGamesPerPlatform: Object[][];
	numberOfReviewsPerPlatform: Object[][];
	avgScorePerPlatform: Object[][];

	constructor(numberOfGamesPerGenre: Object[][], numberOfGamesPerPlatform: Object[][], numberOfReviewsPerPlatform: Object[][], avgScorePerPlatform: Object[][]){
		this.numberOfGamesPerGenre = numberOfGamesPerGenre;
		this.numberOfGamesPerPlatform = numberOfGamesPerPlatform;
		this.numberOfReviewsPerPlatform = numberOfReviewsPerPlatform;
		this.avgScorePerPlatform = avgScorePerPlatform;
	}
}
