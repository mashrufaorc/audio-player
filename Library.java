import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	//variable declaration
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	AudioContentStore contentStore = new AudioContentStore();
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		if(content.getType() == Song.TYPENAME){           //checks to see if content is song 
			for(int i = 0; i < songs.size(); i++){        //iterates through songs arraylist
				if(songs.get(i).equals(content)){         //checks to see if the song is in song arraylist
					throw new AlreadyDownloaded("Song " + content.getTitle() + " already downloaded");  //if song is already in arraylist, throws exception
				}
			}
			songs.add((Song)content);  //if song not in arraylist, then add it to arraylist
			System.out.println("SONG " + content.getTitle() + " Added to Library");    //print statement displaying song is added to the library
		}

		if(content.getType() == AudioBook.TYPENAME){ //checks to see if content is audioBook 
			for(int i = 0; i < audiobooks.size(); i++){   //iterates through audioBook arraylist  
				if(audiobooks.get(i).equals(content)){   //checks if the audioBook is in  audioBook arraylist
					throw new AlreadyDownloaded("AudioBook " + content.getTitle() + " already downloaded");  //if song is already in arraylist, throws exception
				}
			}
			audiobooks.add((AudioBook)content);  //if song not in arraylist, then add it to arraylist
			System.out.println("AUDIOBOOK " + content.getTitle() + " Added to Library");   //print statement displaying audiobook is added to the library
		}
	}

	public void downloadArtistAuthorGenre(AudioContent content){
		//downloading songs
		if(content.getType() == Song.TYPENAME){  //checks to see if content is song
			for(Song song : songs){              //iterates through songs arraylist
				if(song.equals(content)){        //checks to see if the song is already in song arraylist
					throw new AlreadyDownloaded("Song " + content.getTitle() + " already downloaded");       //if song is already in arraylist, throws exception and prints error message
				}
			}		
			songs.add((Song)content);  //if song not in arraylist, then add it to arraylist
		}

		//downloading audiobooks
		if(content.getType() == AudioBook.TYPENAME){ //checks to see if content is audiobook
			for(AudioBook audiobook : audiobooks){   //iterates through audiobooks arraylist
				if(audiobook.equals(content)){       //checks to see if the audiobook is already in audiobooks arraylist
					throw new AlreadyDownloaded("Audiobook " + content.getTitle() + " already downloaded");  //if song is already in arraylist, throws exception and prints error message
				}
			}
			audiobooks.add((AudioBook)content);  //if audiobook not in arraylist, then add it to arraylist
		}
	}

	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)  
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)  //iterates through audioBook arraylist
		{
			int index = i + 1; 
			System.out.print("" + index + ". ");     //prints index of each content
			audiobooks.get(i).printInfo();           //prints info of each audiobook from audiobook arraylist
			System.out.println();	
		}
	}

  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)      //iterates through audioBook arraylist
		{
			int index = i + 1; 
			System.out.print("" + index + ". ");        //prints index of each content
			System.out.println(playlists.get(i).getTitle()); 	  //prints title of each playlist from playlist arraylist
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> newL = new ArrayList<String>();                  //creates new arraylist of string

		for (int i = 0; i < songs.size(); i++){                            //iterates through songs arraylist
			if (!newL.contains(songs.get(i).getArtist())){                 //checks if artist name is not in new arraylist
				newL.add(songs.get(i).getArtist());                        //adds artist to the new arraylist
				int index = i + 1;                                         
				System.out.print("" + index + ". ");                       //prints index of each content
				System.out.println(songs.get(i).getArtist());              //prints artist
			}
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public boolean deleteSong(int index)
	{
		if (index < 1 || index > songs.size())     //checks if index is invalid
		{
			errorMsg = "Song Not Found";           //error message
			return false;
		}
		Song song = songs.remove(index-1);         //removes item from songs arraylist
		
		// Search all playlists
		for (int i = 0; i < playlists.size(); i++)
		{
			Playlist pl = playlists.get(i);
			if (pl.getContent().contains(song))
				pl.getContent().remove(song);
		}
		return true;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		Collections.sort(songs, new SongYearComparator());           //collections.sort to sort the arraylist
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2){         //compare two songs based on year
			if(s1.getYear() < s2.getYear()){          //if song1 year is less than song2 year
				return -1;
			}
			else if (s1.getYear() == s2.getYear()){  //if song1 year is the same as song2 year
				return 0;
			}
			return 1;                                 //else
		} 
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort()
	 Collections.sort(songs, new SongLengthComparator());         //collections.sort to sort the arraylist
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2){        //compare two songs based on length
			if(s1.getLength() < s2.getLength()){     //if song1 length is less than song2 length
				return -1;
			}
			else if (s1.getLength() == s2.getLength()){     //if song1 length is the same as song2 length
				return 0;
			}
			return 1;
		} 
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs, new SongNameComparator());
	}

	private class SongNameComparator implements Comparator<Song>                    //implements the Comparator interface
	{
		public int compare(Song s1, Song s2){                                       //compare two songs based on name
			if(s1.getTitle().charAt(0) < s2.getTitle().charAt(0)){      //if song1 name value is less than song2 name value
				return -1;
			}
			else if (s1.getTitle()== s2.getTitle()){                                //if song1 name is the same as song2 name
				return 0;
			}
			return 1;
		}
	}
	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new NotFound("Song Not Found");  //if not throw exception
		}
		songs.get(index-1).play();    //playing the song
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size())                 //checks to see if index is invalid
		{
			throw new NotFound("Audiobook Not Found");      //if not throw exception
		}
		audiobooks.get(index-1).selectChapter(chapter);             //calls selectChapter method
		audiobooks.get(index-1).play();                             //calls the play method
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())   //checks to see if index is invalid
		{
			throw new NotFound("Audiobook Not Found");    //if not throw exception
		}
		audiobooks.get(index-1).printTOC();  //calls printTOC method
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newPlaylist = new Playlist(title);            //creates new playlist

		for(Playlist playlist : playlists){                   //iterates through playlist arralist
			if(title.equals(playlist.getTitle())){            //checks to see if playlist exists
				throw new AlreadyExists("Playlist " + title + " Already Exists");  //if true then throw exception
			}
		}
		playlists.add(newPlaylist);                            //adds new playlist to playlists arraylist
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		for(int i = 0; i < playlists.size(); i++){               //iterates through playlist arralist
			if(playlists.get(i).getTitle().equals(title)){       //checks if a playlist is in the playlist arraylist
				playlists.get(i).printContents();                //prints the playlist
			}
		}
		throw new NotFound("Playlist Not Found");  //if there are no matching playlists then setting the errormsg 
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		int index = 0;      //variable initialization
		for (int i = 0; i < playlists.size(); i++){                      //iterates through playlist arralist
			if(!playlistTitle.equals(playlists.get(i).getTitle())){      //checks if a playlist is not in the playlist arraylist
				throw new NotFound("Playlist Not Found");                         //error message
			}
			index = i;                                                   
		}
		playlists.get(index).playAll(); 
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		for(int i=0;i<playlists.size();i++){                         //iterates through playlist arralist
			if(playlists.get(i).getTitle().equals(playlistTitle)){   //checks if a playlist is not in the playlist arraylist
				playlists.get(i).play(indexInPL);                    //play the content
			}
		}
		throw new NotFound("Playlist Not Found");            //throw exception
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		for(int i = 0; i < playlists.size(); i++){                               //iterates through playlist arraylist
			if(playlistTitle.equals(playlists.get(i).getTitle())){               //checks if playlist already exists
				if(type.equalsIgnoreCase(Song.TYPENAME)){                        //checks if content is song
					playlists.get(i).addContent(songs.get(index-1));             //add content to playlist
				}
				else if (type.equalsIgnoreCase(AudioBook.TYPENAME)){             //checks if content is audiobook
					playlists.get(i).addContent(audiobooks.get(index-1));        //add content to playlist
				}
			}
		}
		throw new NotFound("Playlist Not Found");                        //throw exception
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		for(int i=0;i<playlists.size();i++){          //iterates through playlist arraylist
			if(playlists.get(i).getTitle().equals(title)){       //checks if playlist already exists
				if (index < 1 || index > playlists.get(i).getContent().size()){  //checks if index is valid
					throw new NotFound("Content Not Found");             //throw exception 
				}
				playlists.get(i).deleteContent(index);     //deletes content from arraylist
			}
		}
		throw new NotFound("Playlist Not Found");
	}

	//extends RunTimeException, checks to see if audio content has already been downloaded
	public class AlreadyDownloaded extends RuntimeException{
		public AlreadyDownloaded() {}
		public AlreadyDownloaded(String message){
			super(message);
		}
	}

	//extends RunTimeException, checks to see if audio content already exists
	public class AlreadyExists extends RuntimeException{
		public AlreadyExists() {}
		public AlreadyExists(String message){
			super(message);
		}
	}
	//extends RunTimeException, checks to see if audio content is found
	public class NotFound extends RuntimeException{
		public NotFound() {}
		public NotFound(String message){
			super(message);
		}
	}
}

