Feature: 004_xpathComplexe

#XMLEntity [id=0, level=0, tag=x1, data=, leaf=false, isChildOf=-1, attributes=null, isFatherOf=[1, 2]]
#XMLEntity [id=1, level=1, tag=x2, data=, leaf=false, isChildOf=0, attributes=null, isFatherOf=[3]]
#XMLEntity [id=2, level=1, tag=x2, data=, leaf=true, isChildOf=0, attributes=null, isFatherOf=null]
#XMLEntity [id=3, level=2, tag=x3, data=, leaf=true, isChildOf=1, attributes={aKey=aValue}, isFatherOf=null]
#<?xml version="1.0" encoding="UTF-8"?>
#<x1>
#  <x2>
#    <x3 aKey="aValue"/>
#  </x2>
#  <x2/>
#</x1>

  Scenario Outline: test xpath du scenario 3
    Given je charge "test.xml"
    When je cherche <xpath>
    Then je dois trouver : <answer> entite

  Examples:
    | xpath                                                                    | answer |
    | "/project/dependencies/dependency/dependency[@test="toto"]/../groupId/"  |  1     |
    