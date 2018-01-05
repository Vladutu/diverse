import {EventEmitter, Injectable} from "@angular/core";

@Injectable()
export class LoadingService {

  public requestEmiter: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() {
  }

  actualize(reqesting: boolean) {
    this.requestEmiter.emit(reqesting);
  }
}
