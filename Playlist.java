import java.util.ArrayList;

/*
 * A Playlist contains an array list of AudioContent (i.e. Song, AudioBooks, Podcasts) from the library
 */
public class Playlist
{
	private String title;
	private ArrayList<AudioContent> contents; // songs, books, or podcasts or even mixture
	
	public Playlist(String title)
	{
		this.title = title;
		contents = new ArrayList<AudioContent>();
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void addContent(AudioContent content)
	{
		contents.add(content);
	}
	
	public ArrayList<AudioContent> getContent()
	{
		return contents;
	}

	public void setContent(ArrayList<AudioContent> contents)
	{
		this.contents = contents;
	}
	
	/*
	 * Print the information of each audio content object (song, audiobook, podcast)
	 * in the contents array list. Print the index of the audio content object first
	 * followed by ". " then make use of the printInfo() method of each audio content object
	 * Make sure the index starts at 1
	 */
	public void printContents()
	{
		for (int i = 0; i < contents.size(); i++){  //iterates through contents arraylist
			System.out.print((i+1) + ". ");         //printing the index of the audio content
			contents.get(i).printInfo();            //printing info of the content by calling printInfo() method
			System.out.println("");
		}
	}

	// Play all the AudioContent in the contents list
	public void playAll()
	{
		for (int i = 0; i < contents.size(); i++){    //iterates through contents arraylist
			contents.get(i).play();                   //playing the content 
			System.out.println("");
		}
	}
	
	// Play the specific AudioContent from the contents array list.
	// First make sure the index is in the correct range. 
	public void play(int index)
	{
		if(contains(index) == true){              //checks if index is in the correct range
			contents.get(index).play();           //plays the specific content 
		}
	}
	
	public boolean contains(int index)
	{
		return index >= 1 && index <= contents.size();
	}
	
	// Two Playlists are equal if their titles are equal
	public boolean equals(Object other)
	{
		Playlist otherObj = (Playlist) other;                       //initializing object  -> casting
		return this.getTitle().equals(otherObj.getTitle());         //checking if both objects are equal
	}
	
	// Given an index of an audio content object in contents array list,
	// remove the audio content object from the array list
	// Hint: use the contains() method above to check if the index is valid
	// The given index is 1-indexed so convert to 0-indexing before removing
	public void deleteContent(int index)
	{
		if (!contains(index)) return;
		contents.remove(index-1);
	}
	
	
}
