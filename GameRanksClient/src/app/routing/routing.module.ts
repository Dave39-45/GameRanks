import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from '../home/home.component';
import { DeveloperComponent } from '../developer/developer.component';
import { DevelopersComponent } from '../developers/developers.component';
import { GameComponent } from '../game/game.component';
import { GamesComponent } from '../games/games.component';
import { PublisherComponent } from '../publisher/publisher.component';
import { PublishersComponent } from '../publishers/publishers.component';
import { RegisterComponent } from '../register/register.component';
import { UserComponent } from '../user/user.component';


const routes: Routes = [
	{
		path: '',
		component: HomeComponent
	},
	{
		path: 'register',
		component: RegisterComponent
	},
	{
		path: 'user',
		component: UserComponent
	},
	{
		path: 'game',
		component: GamesComponent
	},
	{
		path: 'game/:id',
		component: GameComponent
	},
	{
		path: 'publisher',
		component: PublishersComponent
	},
	{
		path: 'publisher/:id',
		component: PublisherComponent
	},
	{
		path: 'developer',
		component: DevelopersComponent
	},
	{
		path: 'developer/:id',
		component: DeveloperComponent
	}
];

@NgModule({
	imports: [ RouterModule.forRoot(routes)  ],
	exports: [ RouterModule ],
	declarations: []
})
export class RoutingModule { }
