//function init(){
//$('#tableProd').append("<tr> <td><input class='form-control' name='quantite[]' type='number'/></td>"
//                       + "<td><input class='form-control' name='prix[]' type='number'/></td>"
//                       + "<td><input class='form-control' name='type[]'  type='type' /></td>"
//                        + "<td> <div class='form-group'> <label class='control-label col-md-2' for='sel5'>Produit</label>"+
//                            +"<div class='col-md-10'><select class='form-control' id='sel5' name='produit.id'>"+
//                             +" <option th:each='produit:${produits}' th:text='${produit.categorie.nom}+' '+${produit.nom}' th:value='${produit.id}'></option></>select>"
//                             +" </div></div> </td> </tr>");
//
//}

$("#plus").on("click",function(){

var $tableBody = $('#tbl').find("tbody"),
        $trLast = $tableBody.find("tr:last"),
        $trNew = $trLast.clone();
        $trLast.after($trNew);
});


