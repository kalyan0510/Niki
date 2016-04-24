var start;
var M=0;
var set=1;
function date_time(id)
{       
        var d = new Date();
        if(start == null)
        {        start=d.getSeconds();
                M=0;
        }      
        var n = d.toISOString();
        var s = d.getSeconds()-start;
        if(s<0)
        s+=60;
                
        if(s==0&&set==0){
                M++;
                set=1;
        }if(s==1){
                set=0;
        }
        var SS;
        if(s<10){
                SS="0"+s;
        }else{
                SS=s;
        }
        var MM;
        if(M<10){
                MM="0"+M;
        }else{
                MM=M;
        }
        
        document.getElementById(id).innerHTML = n+"<br> Time spent- "+MM+":"+SS;
        setTimeout('date_time("'+id+'");','10');
        return true;
}
