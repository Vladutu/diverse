import {Component, OnInit, Input, ViewChild, OnChanges, SimpleChanges} from "@angular/core";
import {Song} from "../model/song";

@Component({
  selector: 'app-song-item',
  templateUrl: './song-item.component.html',
  styleUrls: ['./song-item.component.css']
})
export class SongItemComponent implements OnInit, OnChanges {

  @Input() private index: number = null;

  @Input() private song: Song;

  @ViewChild('audio') private audio;

  constructor() {
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.audio.nativeElement.load();
  }


}
