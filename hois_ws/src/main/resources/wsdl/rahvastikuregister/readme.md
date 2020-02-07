# Changes made in the wsdl file
**dokumendi_tyyp** has been renamed to **dokumendiTyyp2**

# ÖÄÜÕ in the generated java classes
3 files have symbols which **cannot** be used in a java class file:

- **RR19IsikuElukohtResponseType.java** (isikuElukohtVälisriigis)
- **RR29IsikuElukohtResponseType.java** (isikuElukohtVälisriigis)
- **RR63IsikAadrDokResponseType.java** (isikuandmedSünniaeg)

They have to be renamed (**attribute**, **getter**, **setter**, **propOrder** in **@XmlType**) (**@XmlElement must have** this symbol as it is in the wsdl file)