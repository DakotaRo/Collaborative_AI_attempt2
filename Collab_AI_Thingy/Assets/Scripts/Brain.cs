using UnityEngine;

public class Brain : MonoBehaviour
{
    public DNA dna;
    public GameObject eyes;
    bool seeWall;
    public float offenseBlocked = 0;
    LayerMask ignore = 6;
    bool canMove = false;

    public void Init()
    {
        dna = new DNA();
    }

    void OnTriggerEnter(Collider col)
    {
        if(col.gameObject.CompareTag("offense"))
        {
            offenseBlocked++;
            col.gameObject.SetActive(false);
        }
    }

    void Update()
    {
        seeWall = false;
        canMove = true;
        RaycastHit hit;
        Debug.DrawRay(eyes.transform.position, eyes.transform.forward * 1f, Color.red);
        if(Physics.SphereCast(eyes.transform.position, 0.1f, eyes.transform.forward, out hit, 1f, ~ignore))
        {
            if(hit.collider.gameObject.CompareTag("wall"))
            {
                seeWall = true;
                canMove = false;
            }
        }
    }

    void FixedUpdate()
    {
        this.transform.Rotate(0, dna.genes[seeWall], 0);
        if(canMove)
            this.transform.Translate(0, 0, 0.1f);
    }
}