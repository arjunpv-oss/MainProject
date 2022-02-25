from flask import Flask,render_template,request
from DBConnection import Db
app = Flask(__name__)
from flask_mail import Mail, Message

app.config['MAIL_SERVER']='smtp.gmail.com'
app.config['MAIL_PORT'] = 465
app.config['MAIL_USERNAME'] = 'bodyheroskin@gmail.com'
app.config['MAIL_PASSWORD'] = 'bodyskiniqraa'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
mail = Mail(app)

staticpath="C:\\Users\\acer\\PycharmProjects\\Skin\\static\\"

@app.route('/')
def adminlanding():
    return render_template('landing.html')


@app.route('/adminhome')
def adminhome():
    return render_template('admin/adminhome.html')

@app.route('/aboutus')
def aboutus():
    return render_template('admin/Aboutus.html')


@app.route('/login')
def login():
    return render_template('index.html')


@app.route('/login_post',methods=['post'])
def login_post():
    username=request.form['textfield']
    password=request.form['textfield2']
    qry="select lid,`type` from login where username= '"+username+"' and password='"+password+"'"
    print(qry)
    a=Db()
    res=a.selectOne(qry)
    if res is not None:
        if res["type"]=="admin":
            return adminhome()
        else:
            return  "no"
    else:
        return "Nooo"



@app.route('/adddisease')
def adddisease():
    return render_template('admin/Add Disease.html')


@app.route('/adddisease_post',methods=['post'])
def adddisease_post():
    Name=request.form['textfield']
    Description=request.form['textarea']
    Image = request.files['fileField']
    import datetime
    dt = datetime.datetime.now().strftime('%Y-%m-%d-%H-%M-%S')
    Image.save(staticpath + "disease\\" + dt + '.jpg')
    path = "/static/disease/" + dt + '.jpg'
    a=Db()
    qry="insert into disease(disease_name,disease_description,disease_image)values('"+Name+"','"+Description+"','"+path+"')"
    res=a.insert(qry)
    return '''<script> alert('added'); window.location='/adddisease' </script>'''



@app.route('/adddoctor')
def addddoctor():
    return render_template('admin/ADD doctor.html')

@app.route('/adddoctor_post',methods=['post'])
def addddoctor_post():
    Name=request.form['textfield']
    Gender = request.form['RadioGroup1']
    Image=request.files['fileField']
    import datetime
    dt=datetime.datetime.now().strftime('%Y-%m-%d-%H-%M-%S')
    Image.save(staticpath+"doctor\\"+dt+'.jpg')
    path="/static/doctor/"+dt+'.jpg'
    Phonenumber = request.form['textfield2']
    Qualification = request.form['textfield3']
    Experience= request.form['textfield4']
    Place = request.form['textfield5']
    Post = request.form['textfield6']
    Pin = request.form['textfield7']
    District = request.form['textfield8']
    EmailID = request.form['textfield9']
    a = Db()
    import random
    password=random.randint(10000,900000)
    msg = Message('Welcome to Body Hero...! your username is '+str(EmailID)+'and password is' +str(password),sender='bodyheroskin@gmail.com',recipients=[EmailID])
    msg.body = 'Hello..... your login credential from Body Hero-Skin Expert '
    mail.send(msg)
    qry1="insert into login(username,password,type)VALUES ('"+EmailID+"','"+str(password)+"','doctor')"
    res1=a.insert(qry1)
    qry = "insert into doctor(d_name,d_gender,d_image,d_phone,d_qualification,d_experience,d_place,d_pin,d_post,d_district,d_email,dlid)values('" + Name + "','" + Gender + "','"+path+"','"+Phonenumber+"','"+Qualification+"','"+Experience+"','"+Place+"','"+Pin+"','"+Post+"','"+District+"','"+EmailID+"','"+str(res1)+"')"
    res = a.insert(qry)
    return '''<script> alert('added'); window.location='/adddoctor' </script>'''


@app.route('/addschedule/<id>')
def addschedule(id):
    a=Db()
    qry="select * from doctor where dlid='"+str(id)+"'"
    res=a.selectOne(qry)
    return render_template('admin/Add Schedule.html',data=res)

@app.route('/addschedule_post',methods=['post'])
def addschedule_post():
    dlid=request.form['textfield']
    Slots = request.form['textfield1']
    fm = request.form['textfield2']
    to = request.form['textfield3']
    Date = request.form['textfield4']
    a = Db()
    qry = "insert into schedule(dlid,schedule_ftime,schedule_ttime,schedule_date)values('" + dlid + "','"+fm+"','"+to+"','"+Date+"')"
    res = a.insert(qry)
    qry1="insert into slot(scedule_id,slot_number) values('"+str(res)+"','"+Slots+"')"
    res1=a.insert(qry1)
    return '''<script> alert('added'); window.location='/viewdoctor' </script>'''




@app.route('/viewdiseases')
def viewdiseases():
    a = Db()
    qry = "select*from disease"
    res = a.select(qry)
    return render_template('admin/view diseases.html', data=res)


@app.route('/deletediseases/<id>')
def deletediseases(id):
    a = Db()
    qry = "delete from disease where disease_id='"+str(id)+"'"
    res = a.delete(qry)
    return '''<script> alert('deleted'); window.location='/viewdiseases' </script>'''


@app.route('/viewdiseases_post',methods=['post'])
def viewdiseases_post():
    Name=request.form['name']

    a = Db()
    qry = "select*from disease where disease_name like '%"+Name+"%'"
    res = a.select(qry)
    return render_template('admin/view diseases.html', data=res)

@app.route('/updatediseases/<id>')
def updatediseases(id):
    a = Db()
    qry = "select*from disease where disease_id='"+str(id)+"'"
    res = a.selectOne(qry)
    return render_template('admin/edit Disease.html', data=res)


@app.route('/updatediseases_post',methods=['post'])
def updatediseases_post():
    id = request.form['abcd']
    Name = request.form['textfield']
    Description = request.form['textarea']
    a = Db()
    qry = "update disease set  disease_name='"+Name+"',disease_description='"+Description+"' where disease_id='"+str(id)+"'"
    res = a.update(qry)
    return '''<script> alert('updated'); window.location='/viewdiseases' </script>'''



@app.route('/viewdoctorreview')
def viewdoctorreview():
    a=Db()
    qry="SELECT d_review.*,doctor.d_name,patient.patient_name FROM d_review INNER JOIN doctor ON d_review.dlid= doctor.dlid INNER JOIN patient ON patient.plid=d_review.plid"
    res=a.select(qry)
    return render_template('admin/view doctor review.html',data=res)

@app.route('/viewdoctorreview_post',methods=['post'])
def viewdoctorreview_post():
    Name = request.form['name']
    a = Db()
    qry = "SELECT d_review.*,doctor.d_name,patient.patient_name FROM d_review INNER JOIN doctor ON d_review.dlid= doctor.dlid INNER JOIN patient ON patient.plid=d_review.plid where d_name like '%"+Name+"%'"
    res = a.select(qry)
    return render_template('admin/view doctor review.html', data=res)

@app.route('/viewdoctor')
def viewdoctor():
    a = Db()
    qry = "select*from doctor"
    res = a.select(qry)
    return render_template('admin/view doctor.html',data=res)
#
# @app.route('/deletedoctor/<id>')
# def deletedoctor(id):
#     a = Db()
#     qry = "delete from doctor where doctor_id='"+str(id)+"'"
#     res = a.delete(qry)
#     return '''<script> alert('deleted'); window.location='/viewdoctor' </script>'''




@app.route('/viewmore/<dlid>')
def viewmore(dlid):
    a = Db()
    qry = "select*from doctor where dlid='"+dlid+"' "
    res = a.selectOne(qry)
    return render_template('admin/prof.html',i=res)

@app.route('/deletedoctor/<id>')
def deletedoctor(id):
    a = Db()
    qry = "delete from doctor where dlid='"+str(id)+"'"
    res = a.delete(qry)
    qry1 = "delete from login where lid='" + str(id) + "'"
    res1 = a.delete(qry1)
    return '''<script> alert('deleted'); window.location='/viewdoctor' </script>'''



@app.route('/viewdoctor_post',methods=['post'])
def viewdoctor_post():
    Name = request.form['name']
    a = Db()
    qry = "select*from doctor where d_name like '%"+Name+"%'"
    res = a.select(qry)
    return render_template('admin/view doctor.html', data=res)

@app.route('/updatedoctor/<id>')
def updatedoctor(id):
    a = Db()
    qry = "select*from doctor where dlid='"+str(id)+"'"
    res = a.selectOne(qry)
    return render_template('admin/edit doctor.html', data=res)


@app.route('/updatedoctor_post',methods=['post'])
def updatedoctor_post():
    id = request.form['abcd']
    Name = request.form['textfield']
    Gender = request.form['RadioGroup1']
    Phonenumber = request.form['textfield2']
    Qualification = request.form['textfield3']
    Experience = request.form['textfield4']
    Place = request.form['textfield5']
    Post = request.form['textfield6']
    Pin = request.form['textfield7']
    District = request.form['textfield8']
    EmailID = request.form['textfield9']
    a = Db()
    if 'fileField' in request.files:
        Image = request.files['fileField']
        if Image.filename!='':
            import datetime
            dt = datetime.datetime.now().strftime('%Y-%m-%d-%H-%M-%S')
            Image.save(staticpath + "doctor\\" + dt + '.jpg')
            path = "/static/doctor/" + dt + '.jpg'
            qry = "update doctor set  d_name='"+Name+"',d_gender='"+Gender+"',d_image='"+path+"',d_phone='"+Phonenumber+"',d_qualification='"+Qualification+"',d_experience='"+Experience+"',d_place='"+Place+"',d_post='"+Post+"',d_pin='"+Pin+"',d_district='"+District+"',d_email='"+EmailID+"' where dlid='"+str(id)+"'"
            res = a.update(qry)
            print(qry)
        else:
            qry = "update doctor set  d_name='" + Name + "',d_gender='" + Gender + "',d_phone='" + Phonenumber + "',d_qualification='" + Qualification + "',d_experience='" + Experience + "',d_place='" + Place + "',d_post='" + Post + "',d_pin='" + Pin + "',d_district='" + District + "',d_email='" + EmailID + "' where dlid='" + str(id) + "'"
            res = a.update(qry)
            print(qry)
    else:
        qry = "update doctor set  d_name='" + Name + "',d_gender='" + Gender + "',d_phone='" + Phonenumber + "',d_qualification='" + Qualification + "',d_experience='" + Experience + "',d_place='" + Place + "',d_post='" + Post + "',d_pin='" + Pin + "',d_district='" + District + "',d_email='" + EmailID + "' where dlid='" + str(id) + "'"
        res = a.update(qry)
        print(qry)
    return '''<script> alert('updated'); window.location='/viewdoctor' </script>'''


@app.route('/viewfeedback')
def viewfeedback():
    a=Db()
    ls=[]
    res=a.select("select * from review")
    if len(res)>0:
        for i in res:
            if i['type']=='doctor':
                qry1="select * from doctor where dlid='"+str(i['lid'])+"'"
                res1=a.selectOne(qry1)
                ls.append({'content':i['rev_content'],'date':i['rev_date'],'name':res1['d_name'],'image':res1['d_image'],'type':'Doctor'})
            else:
                qry2 = "select * from patient where plid='" + str(i['lid']) + "'"
                res2 = a.selectOne(qry2)
                ls.append({'content': i['rev_content'], 'date': i['rev_date'], 'name': res2['patient_name'],'image': res2['patient_image'],'type':'Patient'})
    print(ls)
    return render_template('admin/view feedback.html',data=ls)

@app.route('/viewfeedback_post',methods=['post'])
def viewfeedback_post():
    DateFrom = request.form['date1']
    DateTo = request.form['date2']
    return render_template('admin/view feedback.html')






if __name__ == '__main__':
    app.run(debug=True)
