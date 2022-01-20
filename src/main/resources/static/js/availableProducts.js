/* TOGGLE  NAVBAR Filtred Page HAMBURGER */
      const toggleButton = document.querySelector('#toggleBtn');
      const naviList = document.querySelector('#navListId');
      const userDivId = document.querySelector('#customerDivId')
       toggleButton.addEventListener('click', () => {
            naviList.classList.toggle('active');
            userDivId.classList.toggle('active');
        })

        /* TOGGLE FILTER NAVBAR Filtred Page HAMBURGER */
      const filterToggleButton=document.querySelector('#toggleFilterButton');
      const navFilterList = document.querySelector('#navFilterList');
      filterToggleButton.addEventListener('click', ()=>{
          navFilterList.classList.toggle('filterActive');
      })

      /*Function to open and close Modal*/
    function openMyModal(){
       let modals = document.querySelectorAll('.modal.myModal')
              // Get the button that opens the modal
        let btns = document.querySelectorAll('.openmodal');
        let spans = document.querySelectorAll(".close");
            for (let i = 0; i < btns.length; i++) {
                btns[i].onclick = function () {
                    modals[i].classList.remove('closeModal');
                    modals[i].classList.add('showModal')
                }
            }
            for (let i = 0; i < spans.length; i++) {
                spans[i].onclick = function () {
                    modals[i].classList.remove('showModal');
                    modals[i].classList.add('closeModal');
                }
            }
    }