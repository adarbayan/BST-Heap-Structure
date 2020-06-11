package code;

import java.util.ArrayList;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

	BinarySearchTree<String, ContactInfo> contactName = new BinarySearchTree<String, ContactInfo>();
	BinarySearchTree<String, ContactInfo> contactNumber = new BinarySearchTree<String, ContactInfo>();

	public PhoneBook() {
		contactName.setComparator(new DefaultComparator<String>());
		contactNumber.setComparator(new DefaultComparator<String>());

	}

	// Returns the number of contacts in the phone book
	public int size() {
		return contactName.size();
	}

	// Returns true if the phone book is empty, false otherwise
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		} else
			return false;
	}

	// Adds a new contact or overwrites an existing contact with the given info
	// Args should be given in the order of e-mail and address which is handled for
	// you
	// Note that it can also be empty. If you do not want to update a field pass
	// null
	// Adds a new contact overwrites an existing contact with the given info
	// Args should be given in the order of e-mail and address which is handled for
	// you
	// Note that it can also be empty. If you do not want to update a field pass
	// null
	public void addContact(String name, String number, String... args) {
		int numArgs = args.length;
		String email = null;
		String address = null;

		ContactInfo contact = new ContactInfo(name, number);

		/*
		 * Add stuff here if needed
		 */

		if (numArgs > 0)
			if (args[0] != null)
				email = args[0];
		if (numArgs > 1)
			if (args[1] != null)
				address = args[1];
		if (numArgs > 2)
			System.out.println("Ignoring extra arguments");

		if (email != null) {
			contact.setEmail(email);
		}
		if (address != null) {
			contact.setAddress(address);
		}

		contactName.put(name, contact);
		contactNumber.put(number, contact);

		// CANNOT WORK ON IT ANYMORE//

	}

	// Return the contact info with the given name
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByName(String name) {
		return contactName.get(name);
	}

	// Return the contact info with the given phone number
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByPhone(String phoneNumber) {
		return contactNumber.get(phoneNumber);
	}

	// Return the contact info with the given e-mail
	// Return null if it does not exists
	// Can be O(n)
	public ContactInfo searchByEmail(String email) {
		for (BinaryTreeNode<String, ContactInfo> node : contactName.getNodesInOrder()) {
			if (node.getValue().getEmail() != null) {
				if (node.getValue().getEmail().equals(email))
					return node.getValue();
			}
		}
		return null;
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given name to delete, false
	// otherwise
	public boolean removeByName(String name) {
		ContactInfo removedOne = contactName.remove(name);
		if (removedOne != null) {
			contactNumber.remove(removedOne.getNumber());
			return true;
		} else {
			return false;
		}
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given number to delete, false
	// otherwise
	public boolean removeByNumber(String phoneNumber) {
		ContactInfo removedOneNumber = contactNumber.remove(phoneNumber);
		if (removedOneNumber != null) {
			contactName.remove(removedOneNumber.getName());
			return true;
		} else {
			return false;
		}
	}

	// Returns the number associated with the name
	public String getNumber(String name) {
		String getNumber = searchByName(name).getNumber();
		return getNumber;
	}

	// Returns the name associated with the number
	public String getName(String number) {
		String getName =searchByPhone(number).getName();
		return getName;
	}

	// Update the email of the contact with the given name
	// Returns true if there is a contact with the given name to update, false
	// otherwise
	public boolean updateEmail(String name, String email) {
		ContactInfo updatedEmail = searchByName(name);
		if (updatedEmail != null) {
			updatedEmail.setEmail(email);
			return true;
		} else {
			return false;
		}
	}

	// Update the address of the contact with the given name
	// Returns true if there is a contact with the given name to update, false
	// otherwise
	public boolean updateAddress(String name, String address) {
		ContactInfo updatedAddress = searchByName(name);
		if (updatedAddress != null) {
			updatedAddress.setAddress(address);
			return true;
		} else {
			return false;
		}
	}

	// Returns a list containing the contacts in sorted order by name
	public List<ContactInfo> getContacts() {
		List<ContactInfo> getContact = new ArrayList<ContactInfo>();
		for (BinaryTreeNode<String, ContactInfo> node : contactName.getNodesInOrder()) {
			if (node.getValue() != null) {
				getContact.add(node.getValue());
			}
		}
		return getContact;
	}

	// Prints the contacts in sorted order by name
	public void printContacts() {
		List<ContactInfo> printList = getContacts();
		for (ContactInfo cont : printList) {
			System.out.println(cont);
		}
	}
}