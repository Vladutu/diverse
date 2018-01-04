import {Component, OnInit} from "@angular/core";
import {Song} from "../model/song";
import {SongService} from "../service/song.service";

@Component({
  selector: 'app-songs-result',
  templateUrl: './songs-result.component.html',
  styleUrls: ['./songs-result.component.css']
})
export class SongsResultComponent implements OnInit {

  private songs: Song[] = [];

  constructor(private songService: SongService) {
  }

  ngOnInit() {
    this.songService.songPublisher.subscribe((s: Song[]) => this.songs = s);
  }

}
