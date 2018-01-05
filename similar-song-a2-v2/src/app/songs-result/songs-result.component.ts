import {Component, OnInit, ViewChild} from "@angular/core";
import {Song} from "../model/song";
import {SongService} from "../service/song.service";
import {BsModalComponent} from 'ng2-bs3-modal';

@Component({
  selector: 'app-songs-result',
  templateUrl: './songs-result.component.html',
  styleUrls: ['./songs-result.component.css']
})
export class SongsResultComponent implements OnInit {

  private songs: Song[] = [];

  private currentSong: Song = null;

  @ViewChild('modal')
  private modal: BsModalComponent;

  constructor(private songService: SongService) {
  }

  ngOnInit() {
    this.songService.songPublisher.subscribe((s: Song[]) => {
      this.songs = s;
      for (let song of this.songs) {
        song.hover = false;
      }
    });
  }

  openModal(song: Song) {
    this.currentSong = song;
    this.modal.open();
  }

  pauseVideo(event) {
    this.currentSong = null;
  }
}
