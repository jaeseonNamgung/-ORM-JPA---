package hellojpa;

import javax.persistence.*;

/*
* 상속 관계 매핑 전략
* 1. 부모 클래스에 단순히 @Entity만 사용 했을 경우 한 테이블에 부모 + 자식 클래스를
* 한번에 매핑 시킨다.
* 2. @Inheritance(strategy = InheritanceType.JOINED) 어노테이션을 사용할 경우 조인 전략을 통해 부모 자식을 상속 시킨다.
* @DiscriminatorColumn 어노테이션을 사용할 경우 각 부모테이블에 각 자식 테이블 type을 만들어준다. (속성 name을 사용할 경우 컬럼 명을
* 변경할 수 있다.)
* 3. @Inheritance(strategy = InheritanceType.SINGLE_TABLE) 어노테이션을 사용할 경우 싱글 테이블 전략을 통해 부모 자식을 상속 시킨다.
* (@DiscriminatorColumn을 필수로 사용 해야 한다., 하지만 없어도 DTYPE이 자동으로 매핑된다.)
* 4. @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) 어노테이션은 부모 테이블을 매핑시키지 않고 자식 클래스만
* 매핑시키는 전략이다. (@DiscriminatorColumn 어노테이션은 사용하지 않는다.)
*
* */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
