package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // cascade 영속성 전이
    // 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때 사용
    // 예: 부모 엔티티를 저장할 때 자식 엔티티도 함께 저장
    // 주의: 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
    // 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐

    // CascadeType.REMOVE 부모 엔티티(Team)에서 관련된 자식 엔티티(Member)를 제거하는 경우
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    List<Child> childList  = new ArrayList<>();

    // 연관 관계 편의 메서드
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }


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

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
