import { SimilarSongA2Page } from './app.po';

describe('similar-song-a2 App', function() {
  let page: SimilarSongA2Page;

  beforeEach(() => {
    page = new SimilarSongA2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
