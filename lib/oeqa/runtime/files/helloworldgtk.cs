using Gtk;
using GLib;
using System;
 
class Hello {
 
	static void Main()
        {
		Application.Init ();

                GLib.Timeout.Add (3000, new GLib.TimeoutHandler (timed_out));
 
                // Set up a button object.
                Button btn = new Button ("Hello World");
                // when this button is clicked, it'll run hello()
                btn.Clicked += new EventHandler (hello);
 
                Window window = new Window ("helloworld");
                // when this window is deleted, it'll run delete_event()
                window.DeleteEvent += delete_event;
 
                // Add the button to the window and display everything
                window.Add (btn);
                window.ShowAll ();
 
                Application.Run ();
        }
 
 
        // runs when the user deletes the window using the "close
        // window" widget in the window frame.
        static void delete_event (object obj, DeleteEventArgs args)
        {
		Application.Quit ();
        }
 
        // runs when the button is clicked.
        static void hello (object obj, EventArgs args)
        {
		Console.WriteLine("Hello World");
                Application.Quit ();
        }

        static bool timed_out ()
        {
	        Console.WriteLine("Timed Out!");
                Application.Quit ();
	        return false;
        }
}
