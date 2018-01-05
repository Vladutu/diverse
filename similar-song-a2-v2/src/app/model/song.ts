import {SongDetails} from './song-details';

export class Song {

  constructor(private id: string, private songDetails: SongDetails, public hover: boolean) {
  }
}
