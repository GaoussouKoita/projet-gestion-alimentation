$(function () {
 //Initialize Select2 Elements
    $('.select2bs4').select2({
      theme: 'bootstrap4'
    });
  })


//input file
$(function () {
  bsCustomFileInput.init();
});

//Toast
var Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000
    });

$('.swalDefaultError').click(function() {
      Toast.fire({
        icon: 'error',
        title: 'Utilisateur supprime.'
      })
    });



$(document).ready(function () {
               // jQuery button click event to add a row
        $('#addBtn').on('click', function () {

            // Adding a row inside the tbody.
            $('#tbody').append(`<tr>
                <td><input class="form-control" name="quantite[]"
                    type="number"/></td>
                <td><input class="form-control" name="prix[]"
                                type="number"/></td>
                <td><input class="form-control" name="type[]"
                                type="text"/></td>
                <td><input class="form-control" name="produit.id[]"
                                type="text"/></td>
            </tr>`);
        });

    });

function ajoutApprovission() {

          var link =  "http://localhost:8080/appro/add";
          console.log(link) ;

             $.ajax({
              url: link,
              type: 'POST',
              contentType: 'application/json',
              data:{"id":1000,"date":"2100-10-07T23:59:59","statutApprovission":"NON_REGLER","montantTotal":1293.0,"montantPaye":10000.0,"montantRestant":-8707.0,"personne":{"id":1,"nom":"HAIDARA","prenom":"Abdoulaye","adresse":"Bamako","telephone":"77777777","typeCompte":"FOURNISSEUR"},"user":{"id":1,"nom":"KOITA","prenom":"Gaoussou","adresse":"Baguineda","telephone":76684788,"login":"admin","password":"$2a$10$hhO2n9WMXpxug8vqtdFx2Oy0pEGPUaq6RzlaEsNRGu34k0I.BP0VG","roles":[{"id":2,"roleName":"UTILISATEUR"},{"id":1,"roleName":"ADMINISTRATEUR"}]},"magasin":{"id":1,"nom":"Auchan","adresse":"Ouest Foire","telephone":"766554413"},"io_produits":[{"id":1,"quantite":11,"prix":11.0,"type":"11,14,14","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":2,"quantite":11,"prix":11.0,"type":"11","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":3,"quantite":14,"prix":14.0,"type":"14,65","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":4,"quantite":1,"prix":1.0,"type":"1,2","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":5,"quantite":14,"prix":14.0,"type":"14","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":6,"quantite":10,"prix":10.0,"type":"10,5,8","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":7,"quantite":1,"prix":1.0,"type":"1,7","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":8,"quantite":1,"prix":1.0,"type":"1,7","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":9,"quantite":10,"prix":44.0,"type":"68","produit":{"id":4,"nom":"2","prix":2.0,"typeEngros":"Bidon","nombreEngros":2,"image":"Ag==","prixEngros":2.0,"codeBarre1":2,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":10,"quantite":4,"prix":4.0,"type":"04","produit":{"id":4,"nom":"2","prix":2.0,"typeEngros":"Bidon","nombreEngros":2,"image":"Ag==","prixEngros":2.0,"codeBarre1":2,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":11,"quantite":10,"prix":10.0,"type":"10","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}}]},
              success: function (response) { console.log(response);},
              error: function (response) {alert("Error");}
                 });
          }

function send(){
    var link =  "http://localhost:8080/appro/add";
    var data = {"id":100,"date":"2100-10-07T23:59:59","statutApprovission":"NON_REGLER","montantTotal":1293.0,"montantPaye":10000.0,"montantRestant":-8707.0,"personne":{"id":1,"nom":"HAIDARA","prenom":"Abdoulaye","adresse":"Bamako","telephone":"77777777","typeCompte":"FOURNISSEUR"},"user":{"id":1,"nom":"KOITA","prenom":"Gaoussou","adresse":"Baguineda","telephone":76684788,"login":"admin","password":"$2a$10$hhO2n9WMXpxug8vqtdFx2Oy0pEGPUaq6RzlaEsNRGu34k0I.BP0VG","roles":[{"id":2,"roleName":"UTILISATEUR"},{"id":1,"roleName":"ADMINISTRATEUR"}]},"magasin":{"id":1,"nom":"Auchan","adresse":"Ouest Foire","telephone":"766554413"},"io_produits":[{"id":1,"quantite":11,"prix":11.0,"type":"11,14,14","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":2,"quantite":11,"prix":11.0,"type":"11","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":3,"quantite":14,"prix":14.0,"type":"14,65","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":4,"quantite":1,"prix":1.0,"type":"1,2","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":5,"quantite":14,"prix":14.0,"type":"14","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":6,"quantite":10,"prix":10.0,"type":"10,5,8","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":7,"quantite":1,"prix":1.0,"type":"1,7","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":8,"quantite":1,"prix":1.0,"type":"1,7","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":9,"quantite":10,"prix":44.0,"type":"68","produit":{"id":4,"nom":"2","prix":2.0,"typeEngros":"Bidon","nombreEngros":2,"image":"Ag==","prixEngros":2.0,"codeBarre1":2,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":10,"quantite":4,"prix":4.0,"type":"04","produit":{"id":4,"nom":"2","prix":2.0,"typeEngros":"Bidon","nombreEngros":2,"image":"Ag==","prixEngros":2.0,"codeBarre1":2,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}},{"id":11,"quantite":10,"prix":10.0,"type":"10","produit":{"id":1,"nom":"Lait Candia","prix":600.0,"typeEngros":"Bidon","nombreEngros":6,"image":"","prixEngros":500.0,"codeBarre1":1234567,"codeBarre2":548484,"categorie":{"id":1,"nom":"Lait"}}}]};

          var request = new XMLHttpRequest();
          request.open("POST", link, false);
          request.send(data);
          console.log(request.status);
}


function saving(){

    $("#save").click(function(){
         $('form').submit(function(event){
             event.preventDefault();
             var tokenHash=jQuery("input[name=_csrf]").val();
                    $.ajax({
                    url: "http://localhost:8080/approvission/add",
                    beforeSend: function (xhr){
                    xhr.setRequestHeader('X-CSRF-Token' , tokenHash);},
                    type: "POST",
                    data: $('form').serialize(),
                    async:false,
                    cache: false,
                    dataType: 'json',
                    success: function (response){
                     console.log("success");}

                     });
             })
         })}
