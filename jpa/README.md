User<->Detail @OneToOne

User->Role @OneToOne

User->Order @OneToMany

Role->User @ManyToOne

Order->User @ManyToOne

Order<->Product @ManyToMany