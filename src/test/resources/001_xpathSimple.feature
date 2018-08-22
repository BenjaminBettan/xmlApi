Feature: 001_xpathSimple

#XMLEntity [id=0, level=0, tag=x1, data=, leaf=false, isChildOf=-1, attributes=null, isFatherOf=[2]]
#XMLEntity [id=2, level=1, tag=x2, data=, leaf=false, isChildOf=0, attributes=null, isFatherOf=[1]]
#XMLEntity [id=1, level=2, tag=y1, data=, leaf=false, isChildOf=2, attributes=null, isFatherOf=[3]]
#XMLEntity [id=3, level=1, tag=y2, data=, leaf=true, isChildOf=1, attributes=null, isFatherOf=null]
#<?xml version="1.0" encoding="UTF-8"?>
#<root>
#  <a>
#    <b>
#      <c/>
#    </b>
#  </a>
#</root>

  Scenario Outline: test xpath du scenario 1
    Given je charge scenario 1
    When je cherche <xpath>
    Then je dois trouver : <answer> entite

  Examples:
    | xpath           | answer |
    | "/root/a/b/c/"  |  1     |
    | "/root/a/"      |  1     |    
