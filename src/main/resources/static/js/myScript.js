//Clone prod vente
$("#plus").on("click",function(){

var $tableBody = $('#tbl').find("tbody"),
        $trLast = $tableBody.find("tr:last"),
        $trNew = $trLast.clone();
        $trLast.after($trNew);
});


//Input in Select box
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



//new prod approvision
$(document).ready(function () {
               // jQuery button click event to add a row
               var rowIdx =1;

        $('#addBtn').on('click', function () {
            // Adding a row inside the tbody.
            $('#tbody').append(`<tr>
                <td><input class="form-control" name="io_produits[${rowIdx}].produit.codeBarre1"
                     placeholder="Sacnner le produit"   required  type="text"/></td>
                <td><input class="form-control" name="io_produits[${rowIdx}].quantite"
                    type="number" required placeholder="Entrer la quantite"  /></td>
                <td><input class="form-control" name="io_produits[${rowIdx}].prix"
                              type="number" required placeholder="Entrer le prix d'achat par unite de type"  /></td>
                <td><input class="form-control" name="io_produits[${rowIdx}].type"
                            placeholder="Bidon, Boite, Sac ... "      type="text" required/></td>

            </tr>`);
            rowIdx++;
        });

    });


//new prod Vente
$(document).ready(function () {
               // jQuery button click event to add a row
               var rowIdx =1;

        $('#addBtnVente').on('click', function () {
            // Adding a row inside the tbody.
            $('#tbody').append(`<tr>
            <td><input class="form-control"  name="io_produits[${rowIdx}].produit.codeBarre1"
                    placeholder="Sacnner le produit" required type="number"/></td>
            <td><input class="form-control" name="io_produits[${rowIdx}].quantite"
                    placeholder="Entrer la quantite" required type="number"/></td>
            <td><select  class="form-control select2bs4" name="io_produits[${rowIdx}].type">
                <option  value="Details">Details</option>
                <option  value="Engros">Engros</option>
            </select></td>
             </tr>`);
            rowIdx++;
        });

    });



//ToolTip
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();
});


//Dropdown
 $('ul.nav li.nav-item').hover(function () {
         $(this).find('.nav-treeview').stop(true, true).delay(200).fadeIn(500); },
         function () {
            $(this).find('.nav-treeview').stop(true, true).delay(50).fadeOut(500);
    });



// chart

var path= jQuery(location).attr('pathname')
if(path==='/'){
 $.getJSON('http://localhost:8080/chart-vente', function(data){
    var prods=[];
    $.each(data, function(key, val){
        prods.push(val);
    });
 var my_labels=[];
 var values=[];
 for(var i=0; i<prods.length; i++){
    my_labels.push(prods[i].produit['nom'])
    values.push(prods[i].quantite)
 }

     'use strict'

      var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
      }

      var mode = 'index'
      var intersect = true

      var $venteChart = $('#vente-chart')
      // eslint-disable-next-line no-unused-vars
      var venteChart = new Chart($venteChart, {
        type: 'bar',
        data: {
          labels: my_labels,
          datasets: [
            {
              backgroundColor: '#007bff',
              borderColor: '#007bff',
              data: values
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          tooltips: {
            mode: mode,
            intersect: intersect
          },
          hover: {
            mode: mode,
            intersect: intersect
          },
          legend: {
            display: false
          },
          scales: {
            yAxes: [{
              // display: false,
              gridLines: {
                display: true,
                lineWidth: '2px',
                color: 'rgba(0, 0, 0, .2)',
                zeroLineColor: 'transparent'
              },
              ticks: $.extend({
                beginAtZero: true,
                callback: function (value) {
                  return value
                }
              }, ticksStyle)
            }],
            xAxes: [{
              display: true,
              gridLines: {
                display: false
              },
              ticks: ticksStyle
            }]
          }
        }
      })


});
}
if(path==='/details'){
    var params=new URLSearchParams(window.location.search)
    params.has('date1')
    var date1=params.get('date1')
    params.has('date2')
    var date2=params.get('date2')

    $.getJSON('http://localhost:8080/chart-vente-between?date1='+date1+'&date2='+date2, function(data){
    var prods=[];
    $.each(data, function(key, val){
        prods.push(val);
    });
 var my_labels=[];
 var values=[];
 for(var i=0; i<prods.length; i++){
    my_labels.push(prods[i].produit['nom'])
    values.push(prods[i].quantite)
 }

     'use strict'

      var ticksStyle = {
        fontColor: '#495057',
        fontStyle: 'bold'
      }

      var mode = 'index'
      var intersect = true

      var $venteChart = $('#vente-chart')
      // eslint-disable-next-line no-unused-vars
      var venteChart = new Chart($venteChart, {
        type: 'bar',
        data: {
          labels: my_labels,
          datasets: [
            {
              backgroundColor: '#007bff',
              borderColor: '#007bff',
              data: values
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          tooltips: {
            mode: mode,
            intersect: intersect
          },
          hover: {
            mode: mode,
            intersect: intersect
          },
          legend: {
            display: false
          },
          scales: {
            yAxes: [{
              // display: false,
              gridLines: {
                display: true,
                lineWidth: '2px',
                color: 'rgba(0, 0, 0, .2)',
                zeroLineColor: 'transparent'
              },
              ticks: $.extend({
                beginAtZero: true,
                callback: function (value) {
                  return value
                }
              }, ticksStyle)
            }],
            xAxes: [{
              display: true,
              gridLines: {
                display: false
              },
              ticks: ticksStyle
            }]
          }
        }
      })
});
    }
