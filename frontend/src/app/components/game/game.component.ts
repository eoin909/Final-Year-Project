import {
  Component,
  OnInit,
  HostListener,
  ElementRef,
  ViewChild,
} from '@angular/core';

import { Player } from './../../models/player'
import { Movement } from './../../models/movement'
import { AppComponent } from '../../app.component'
import { GameService } from '../../services/game.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss']
})


export class GameComponent implements OnInit {
  constructor(private game: GameService, private appComponent: AppComponent) {}

  @ViewChild('myCanvas') myCanvas: ElementRef;
  public context: CanvasRenderingContext2D;  private width : number = 1200
  private height : number = 400
  private player : Player;
  private playerArray : Array<Player> = [];
  private userName: string = this.appComponent.username || 'user' + Math.floor((Math.random()*1000)+1);

  ngOnInit() {
    this.context = (<HTMLCanvasElement>this.myCanvas.nativeElement).getContext('2d');

    this.game.gameUpdates.subscribe(msg => {
      this.playerArray = msg
      this.draw();
    })
    this.game.playerJoins.subscribe(msg => {
      this.player = msg
    })
    this.game.sendPlayerJoin(this.userName)
  }

  ngAfterViewInit(): void {
    this.draw()
  }

  ngOnDestroy(): void {
    this.game.sendPlayerLeave(this.userName)
  }

  draw() {
    const ctx = this.context;
    const HIT_COLOR = '#06080c';
    ctx.fillStyle = HIT_COLOR;
    ctx.fillRect(0, 0, this.width, this.height)

    this.playerArray.map(player => {
      ctx.fillStyle = player.color;
      ctx.fillRect(player.xpos, player.ypos, 10, 10)
    })
  }

  @HostListener('window:keydown', ['$event'])
  keyEvent(event: KeyboardEvent) {
    const move = new Movement();

    switch(event.key){
      case "ArrowDown":
      move.userName = this.userName;
      move.xMovement = 0;
      move.yMovement = 10;

      this.game.sendGameUpdates(move)
      break;

      case 'ArrowUp':
      move.userName = this.userName;
      move.xMovement = 0;
      move.yMovement = -10;
      this.game.sendGameUpdates(move)
      break;

      case 'ArrowLeft':
      move.userName = this.userName;
      move.xMovement = -10;
      move.yMovement = 0;
      this.game.sendGameUpdates(move)
      break;

      case 'ArrowRight':
      move.userName = this.userName;
      move.xMovement = 10;
      move.yMovement = 0;
      this.game.sendGameUpdates(move)
      break;
    }
  }
}