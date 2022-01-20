

 function togglePassword() {
    let x = document.querySelector("#pwd");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }

  document.querySelector('#togglePwdBtn').addEventListener('click', togglePassword);

   //Form validation
    const fullName = document.querySelector('#fullNameTxt');
    const phone = document.querySelector('#phoneTxt');
    const pass = document.querySelector('#pwd');
    const username = document.querySelector('#userNameTxt');
    const email = document.querySelector('#emailTxt');

    const errorN = document.querySelector('#errorMessageN');
    const errorPhone = document.querySelector('#errorMessagePh');
    const errorP = document.querySelector('#errorMessageP');
    const errorU = document.querySelector('#errorMessageU')
    const errorE = document.querySelector('#errorMessageE');

  function validateFullName(){
       if(fullName.value.length < 4){
            fullName.classList.add('bad');
            errorN.classList.add('error');
            fullName.focus();

        }else{
            fullName.classList.remove('bad');
            errorN.classList.remove('error');
            return true;
        }
  }

  function validatePhone(){
          let phoneNo = /^\d{10}$/;
           if(!(phone.value.match(phoneNo))){
               phone.classList.add('bad');
               errorPhone.classList.add('error');
               phone.focus();
            }else{
               phone.classList.remove('bad');
               errorPhone.classList.remove('error');
               return true;
            }
    }

    function validatePassword(){
        if(!(pass.value.match(/\d/)) || (pass.value.length < 4)){
            pass.classList.add('bad');
            errorP.classList.add('error');
            pass.focus();
        }else{
            pass.classList.remove('bad');
            errorP.classList.remove('error');
            return true;
        }

    }

    function validateUserName(){
        if(username.value.length < 4){
           username.classList.add('bad');
           errorU.classList.add('error');
           username.focus();
        }else{
           username.classList.remove('bad');
           errorU.classList.remove('error');
           return true;
        }
    }

    function validateEmail(){
        let emailRe = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(!email.value.match(emailRe)){
            email.classList.add('bad');
            errorMessageE.classList.add('error');
            email.focus();
        }else{
            email.classList.remove('bad');
            errorMessageE.classList.remove('error');
            return true;
        }
    }

    function validateElements(event){

        if(validateFullName() &&
            validatePhone() &&
            validatePassword() &&
            validateUserName() &&
            validateEmail()) {
            return true;
            }else{
            event.preventDefault();
            }
    }
    document.querySelector('#submitBtn').addEventListener('click',validateElements);


