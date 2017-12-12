import {PublisherStruct} from "../element/PublisherStruct";
import {StatisticStruct} from "../element/StatisticStruct";

export class PublisherPageStruct{
	publisherStruct: PublisherStruct;
	statisticStruct: StatisticStruct;

	constructor(publisherStruct: PublisherStruct, statisticStruct: StatisticStruct){
		this.publisherStruct = publisherStruct;
		this.statisticStruct = statisticStruct;
	}
}
