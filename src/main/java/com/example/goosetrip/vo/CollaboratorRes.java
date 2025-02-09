package com.example.goosetrip.vo;

import java.util.List;

public class CollaboratorRes extends BasicRes {
	private List<Collaborator> collaboratorlist;

	public CollaboratorRes() {
		super();
	}

	public CollaboratorRes(int code, String message) {
		super(code, message);
	}

	public CollaboratorRes(int code, String message,List<Collaborator> collaboratorlist) {
		super(code, message);
		this.collaboratorlist = collaboratorlist;
	}

	public List<Collaborator> getCollaboratorlist() {
		return collaboratorlist;
	}

	public void setCollaboratorlist(List<Collaborator> collaboratorlist) {
		this.collaboratorlist = collaboratorlist;
	}
	
	

}
