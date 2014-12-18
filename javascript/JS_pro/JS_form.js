function onBodyLoad()
{
	hider();
}

function onValidate()
{
	if(validateName())
	{
		document.getElementById("nameError").style.visibility="visible";
	}
	else
	{
		document.getElementById("nameError").style.visibility="hidden";
	}
	
	if(validateGender())
	{
		document.getElementById("genderError").style.visibility="visible";
	}
	else
	{
		document.getElementById("genderError").style.visibility="hidden";
	}
	
	if(validateCity())
	{
		document.getElementById("cityError").style.visibility="visible";
	}
	else
	{
		document.getElementById("cityError").style.visibility="hidden";
	}
		
	if(validateTandM())
	{
		document.getElementById("tandmError").style.visibility="visible";
	}
	else
	{
		document.getElementById("tandmError").style.visibility="hidden";
	}
}

function hider()
{
	document.getElementById("nameError").style.visibility="hidden";
	document.getElementById("genderError").style.visibility="hidden";
	document.getElementById("cityError").style.visibility="hidden";
	document.getElementById("tandmError").style.visibility="hidden";
}

function validateName()
{
	var nameInputBox = document.getElementById("textInput");
	
	if( nameInputBox.value  == '')
	{
		return true;
	}
	
	else
	{
		return false;
	}
}
function validateGender()
{
	var radioBox = document.frm.gender;
	var len = radioBox.length;
	for( var i = 0 ; i < len ; i++ )
	{
		if( radioBox[i].checked )
		{
			return false;
		}
	}
	return true;
}

function validateCity()
{
	if( document.frm.citySelect.selectedIndex == 0 )
	{
		return true;
	}
	
	else
	{
		return false;
	}
	
}

function validateTandM()
{
	return !( document.frm.cb1.checked );
}