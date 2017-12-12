import {DeveloperStruct} from "../element/DeveloperStruct";
import {StatisticStruct} from "../element/StatisticStruct";

export class DeveloperPageStruct{
	developerStruct: DeveloperStruct;
	statisticStruct: StatisticStruct;

	constructor(developerStruct: DeveloperStruct, statisticStruct: StatisticStruct){
		this.developerStruct = developerStruct;
		this.statisticStruct = statisticStruct;
	}
}
